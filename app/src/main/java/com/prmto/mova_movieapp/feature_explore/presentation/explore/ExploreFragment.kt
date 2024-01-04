package com.prmto.mova_movieapp.feature_explore.presentation.explore

import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.prmto.core_domain.models.Category
import com.prmto.core_domain.repository.isAvaliable
import com.prmto.core_ui.base.fragment.BaseFragmentWithUiEvent
import com.prmto.core_ui.base.isEmpty
import com.prmto.core_ui.util.collectFlow
import com.prmto.core_ui.util.handlePagingLoadState.HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter
import com.prmto.core_ui.util.loadAd
import com.prmto.core_ui.util.makeGone
import com.prmto.core_ui.util.makeVisible
import com.prmto.explore_ui.adapter.FilterMoviesAdapter
import com.prmto.explore_ui.adapter.FilterTvSeriesAdapter
import com.prmto.explore_ui.adapter.HandlePagingStateSearchAdapter
import com.prmto.explore_ui.adapter.SearchRecyclerAdapter
import com.prmto.explore_ui.databinding.FragmentExploreBinding
import com.prmto.explore_ui.event.ExploreFragmentEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.prmto.core_ui.R as CoreUiR

@AndroidEntryPoint
class ExploreFragment :
    BaseFragmentWithUiEvent<FragmentExploreBinding, ExploreViewModel>(
        inflater = FragmentExploreBinding::inflate
    ) {
    override lateinit var viewModel: ExploreViewModel
    private val searchRecyclerAdapter: SearchRecyclerAdapter by lazy { SearchRecyclerAdapter() }
    private val movieFilterAdapter: FilterMoviesAdapter by lazy { FilterMoviesAdapter() }
    private val tvFilterAdapter: FilterTvSeriesAdapter by lazy { FilterTvSeriesAdapter() }
    private var movieDiscoverJob: Job? = null
    private var tvDiscoverJob: Job? = null
    private var searchJob: Job? = null
    private var job: Job? = null

    override fun onInitialize() {
        viewModel = ViewModelProvider(requireActivity())[ExploreViewModel::class.java]
        binding.adView.loadAd()
        observeConnectivityStatus()
        collectUiEvent()
        setupAdapters()
        addTextChangedListener()
        handlePagingLoadStates()
        setClickListeners()
    }

    private fun observeConnectivityStatus() {
        collectFlow(viewModel.networkState) { networkState ->
            if (networkState.isAvaliable()) {
                job?.cancel()
                hideErrorScreenAndShowDetailScreen()
                collectData()
            } else {
                if (isAdaptersEmpty()) {
                    showErrorScreenAndHideDetailScreen()
                }
                job?.cancel()
            }
        }
    }

    private fun isAdaptersEmpty(): Boolean {
        return movieFilterAdapter.isEmpty() || tvFilterAdapter.isEmpty() || searchRecyclerAdapter.itemCount <= 0
    }

    private fun addTextChangedListener() {
        binding.edtQuery.addTextChangedListener {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(400)
                it?.let {
                    viewModel.onEventExploreFragment(ExploreFragmentEvent.MultiSearch(query = it.toString()))
                }
            }
        }
    }

    private fun collectData() {
        collectFlow(viewModel.query) { query ->
            binding.edtQuery.setText(query)
        }

        job = collectFlow(viewModel.filterBottomSheetState) { filterBottomState ->
            when (filterBottomState.categoryState) {
                Category.MOVIE -> {
                    viewModel.onEventExploreFragment(ExploreFragmentEvent.RemoveQuery)
                    movieDiscoverJob = collectFlow(viewModel.discoverMovie()) {
                        hideTvAndSearchAdapters()
                        cancelTvAndSearchJobs()
                        binding.recyclerDiscoverMovie.makeVisible()
                        movieFilterAdapter.submitData(it)
                    }
                }

                Category.TV -> {
                    viewModel.onEventExploreFragment(ExploreFragmentEvent.RemoveQuery)
                    tvDiscoverJob = collectFlow(viewModel.discoverTv()) {
                        cancelMovieAndSearchJobs()
                        hideMoviesAndSearchAdapter()
                        binding.recyclerDiscoverTv.makeVisible()
                        tvFilterAdapter.submitData(it)
                    }
                }

                Category.SEARCH -> {
                    viewModel.query.collectLatest { query ->
                        binding.edtQuery.setSelection(query.length)
                        searchJob = collectFlow(viewModel.multiSearch(query)) {
                            cancelTvAndMovieJobs()
                            hideTvAndMovieAdapters()
                            binding.recyclerSearch.makeVisible()
                            searchRecyclerAdapter.submitData(it)
                        }
                    }
                }
            }
        }
    }

    private fun collectUiEvent() {
        collectFlow(viewModel.consumableViewEvents) { listOfEvents ->
            if (listOfEvents.isNotEmpty()) {
                when (val event = listOfEvents.first()) {
                    is com.prmto.core_ui.util.UiEvent.ShowSnackbar -> {
                        showSnackbar(uiText = event.uiText)
                    }

                    is com.prmto.core_ui.util.UiEvent.PopBackStack -> {
                        findNavController().popBackStack()
                    }

                    is com.prmto.core_ui.util.UiEvent.NavigateTo -> {
                        findNavController().navigate(event.directions)
                    }
                }
            }
        }
    }

    private fun setupAdapters() {
        binding.recyclerSearch.adapter = searchRecyclerAdapter
        binding.recyclerDiscoverMovie.adapter = movieFilterAdapter
        binding.recyclerDiscoverTv.adapter = tvFilterAdapter
    }

    private fun showErrorScreenAndHideDetailScreen() {
        binding.detailScreen.makeGone()
        binding.errorScreen.makeVisible()
    }

    private fun hideErrorScreenAndShowDetailScreen() {
        binding.detailScreen.makeVisible()
        binding.errorScreen.makeGone()
    }

    private fun setBtnErrorClickListener() {
        binding.btnError.setOnClickListener {
            if (viewModel.isNetworkAvaliable()) {
                collectData()
                hideErrorScreenAndShowDetailScreen()
            } else {
                Toast.makeText(
                    requireContext(), getString(CoreUiR.string.internet_error), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun handlePagingLoadStates() {
        HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter(
            pagingAdapter = tvFilterAdapter,
            onLoading = {
                binding.filterShimmerLayout.makeVisible()
                binding.recyclerDiscoverTv.makeGone()
            },
            onNotLoading = {
                binding.filterShimmerLayout.makeGone()
                binding.recyclerDiscoverTv.makeVisible()
            },
            onError = { showErrorScreenAndHideDetailScreen() }
        )

        HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter(
            pagingAdapter = movieFilterAdapter,
            onLoading = {
                binding.filterShimmerLayout.makeVisible()
                binding.recyclerDiscoverMovie.makeGone()
            },
            onNotLoading = {
                binding.filterShimmerLayout.makeGone()
                binding.recyclerDiscoverMovie.makeVisible()
            },
            onError = { showErrorScreenAndHideDetailScreen() }
        )

        HandlePagingStateSearchAdapter(
            searchPagingAdapter = searchRecyclerAdapter,
            onLoading = {
                binding.filterShimmerLayout.makeVisible()
                binding.recyclerSearch.makeGone()
            },
            onNotLoading = {
                binding.filterShimmerLayout.makeGone()
                binding.recyclerSearch.makeVisible()
            },
            onError = { showErrorScreenAndHideDetailScreen() }
        )
    }

    private fun searchRecyclerAdapterListeners() {
        val action = ExploreFragmentDirections.actionExploreFragmentToDetailBottomSheet(null, null)
        setupSearchRecyclerAdapterListener(action = action)
        movieFilterAdapter.setOnItemClickListener { movie ->
            action.movie = movie
            action.tvSeries = null
            navigateToDetailBottomSheet(action = action)
        }
        tvFilterAdapter.setOnItemClickListener { tvSeries ->
            action.movie = null
            action.tvSeries = tvSeries
            navigateToDetailBottomSheet(action = action)
        }
    }

    private fun setupSearchRecyclerAdapterListener(
        action: ExploreFragmentDirections.ActionExploreFragmentToDetailBottomSheet
    ) {
        searchRecyclerAdapter.setOnTvSearchClickListener { tvSeries ->
            action.movie = null
            action.tvSeries = tvSeries
            navigateToDetailBottomSheet(action = action)
        }
        searchRecyclerAdapter.setOnMovieSearchClickListener { movie ->
            action.movie = movie
            action.tvSeries = null
            navigateToDetailBottomSheet(action = action)
        }
        searchRecyclerAdapter.setOnPersonSearchClickListener { person ->
            val action =
                ExploreFragmentDirections.actionExploreFragmentToPersonDetailFragment(person.id)

            findNavController().navigate(action)
        }
    }

    private fun navigateToDetailBottomSheet(action: ExploreFragmentDirections.ActionExploreFragmentToDetailBottomSheet) {
        findNavController().navigate(action)
    }

    private fun setClickListeners() {
        binding.filter.setOnClickListener {
            findNavController().navigate(ExploreFragmentDirections.actionExploreFragmentToFilterBottomSheetFragment())
        }
        searchRecyclerAdapterListeners()
        setBtnErrorClickListener()
    }

    private fun cancelMovieAndSearchJobs() {
        searchJob?.cancel()
        movieDiscoverJob?.cancel()
    }

    private fun cancelTvAndSearchJobs() {
        searchJob?.cancel()
        tvDiscoverJob?.cancel()
    }

    private fun cancelTvAndMovieJobs() {
        tvDiscoverJob?.cancel()
        movieDiscoverJob?.cancel()
    }

    private fun hideMoviesAndSearchAdapter() {
        binding.recyclerDiscoverMovie.makeGone()
        binding.recyclerSearch.makeGone()
    }

    private fun hideTvAndSearchAdapters() {
        binding.recyclerSearch.makeGone()
        binding.recyclerDiscoverTv.makeGone()
    }

    private fun hideTvAndMovieAdapters() {
        binding.recyclerDiscoverTv.makeGone()
        binding.recyclerDiscoverMovie.makeGone()
    }
}
