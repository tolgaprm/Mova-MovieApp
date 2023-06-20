package com.prmto.mova_movieapp.feature_mylist.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.material.tabs.TabLayout
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.adapter.MovieAdapter
import com.prmto.mova_movieapp.core.presentation.adapter.TvSeriesAdapter
import com.prmto.mova_movieapp.core.presentation.util.BaseUiEvent
import com.prmto.mova_movieapp.core.presentation.util.collectFlow
import com.prmto.mova_movieapp.databinding.FragmentMyListBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_my_list) {

    private var _binding: FragmentMyListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListViewModel by viewModels()

    private val movieAdapter: MovieAdapter by lazy { MovieAdapter() }
    private val tvSeriesAdapter: TvSeriesAdapter by lazy { TvSeriesAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMyListBinding.bind(view)
        _binding = binding

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        addListTypeChipGroupListener()

        addTabLayoutSelectedListener()

        collectData()
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

    private fun collectData() {
        collectUiState()
        collectListState()
    }

    private fun collectUiState() {
        collectFlow(viewModel.eventFlow) { event ->
            when (event) {
                is BaseUiEvent.NavigateTo -> {
                    findNavController().navigate(event.directions)
                }

                else -> return@collectFlow
            }
        }
    }

    private fun collectListState() {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}