package com.prmto.mova_movieapp.feature_mylist.presentation

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.prmto.mova_movieapp.core.presentation.adapter.MovieAdapter
import com.prmto.mova_movieapp.core.presentation.adapter.TvSeriesAdapter
import com.prmto.mova_movieapp.core.presentation.base.fragment.BaseFragmentWithUiEvent
import com.prmto.mova_movieapp.core.presentation.util.collectFlow
import com.prmto.mova_movieapp.core.presentation.util.loadAd
import com.prmto.mova_movieapp.databinding.FragmentMyListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : BaseFragmentWithUiEvent<FragmentMyListBinding, ListViewModel>(
    inflater = FragmentMyListBinding::inflate
) {
    override val viewModel: ListViewModel by viewModels()
    private val movieAdapter: MovieAdapter by lazy { MovieAdapter() }
    private val tvSeriesAdapter: TvSeriesAdapter by lazy { TvSeriesAdapter() }

    override fun onInitialize() {
        binding.adView.loadAd()
        addListTypeChipGroupListener()
        addTabLayoutSelectedListener()
        collectUiState()
        collectUiEvent()
    }

    private fun collectUiState() {
        collectFlow(viewModel.state) { state ->
            binding.progressBar.isVisible = state.isLoading
            when (state.chipType) {
                ChipType.MOVIE -> {
                    movieAdapter.submitList(state.movieList)
                    binding.recyclerView.swapAdapter(movieAdapter, true)

                    movieAdapter.setOnclickListener { movie ->
                        viewModel.onEvent(ListEvent.ClickedMovieItem(movie))
                    }
                }

                ChipType.TVSERIES -> {
                    tvSeriesAdapter.submitList(state.tvSeriesList)
                    binding.recyclerView.swapAdapter(tvSeriesAdapter, true)
                    tvSeriesAdapter.setOnclickListener { tvSeries ->
                        viewModel.onEvent(ListEvent.ClickedTvSeriesItem(tvSeries))
                    }
                }
            }
        }
    }

    private fun collectUiEvent() {
        collectFlow(viewModel.consumableViewEvents) { event ->
            handleUiEvent(
                event,
                onEventConsumed = viewModel::onEventConsumed
            )
        }
    }

    private fun addListTypeChipGroupListener() {
        binding.listTypeChipGroup.setOnCheckedStateChangeListener { group, _ ->
            val chipType =
                if (group.checkedChipId == binding.movieChip.id) ChipType.MOVIE else ChipType.TVSERIES
            viewModel.onEvent(ListEvent.UpdateListType(chipType = chipType))
        }
    }

    private fun addTabLayoutSelectedListener() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    val listTab = if (tab.position == 0) ListTab.FAVORITELIST else ListTab.WATCHLIST
                    viewModel.onEvent(ListEvent.SelectedTab(listTab))
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
}