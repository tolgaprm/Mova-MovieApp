package com.prmto.mova_movieapp.feature_mylist.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.util.BaseUiEvent
import com.prmto.mova_movieapp.databinding.FragmentMyListBinding
import com.prmto.mova_movieapp.feature_mylist.presentation.adapter.MovieAdapter
import com.prmto.mova_movieapp.feature_mylist.presentation.adapter.TvSeriesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


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

        binding.listTypeChipGroup.setOnCheckedStateChangeListener { group, _ ->
            val listType =
                if (group.checkedChipId == binding.movieChip.id) ListType.MOVIE else ListType.TVSERIES
            viewModel.onEvent(ListEvent.UpdateListType(listType = listType))
        }

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


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    collectUiState()
                }

                launch {
                    collectListState()
                }
            }

        }

    }

    private suspend fun collectUiState() {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is BaseUiEvent.NavigateTo -> {
                    findNavController().navigate(event.directions)
                }
                else -> return@collectLatest
            }
        }
    }

    private suspend fun collectListState() {
        viewModel.state.collectLatest { state ->

            binding.progressBar.isVisible = state.isLoading

            when (state.whichTypeOfList) {
                ListType.MOVIE -> {
                    movieAdapter.submitList(state.movieList)
                    binding.recyclerView.swapAdapter(movieAdapter, true)

                    movieAdapter.setOnclickListener { movie ->
                        viewModel.onEvent(ListEvent.ClickedMovieItem(movie))
                    }
                }
                ListType.TVSERIES -> {
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