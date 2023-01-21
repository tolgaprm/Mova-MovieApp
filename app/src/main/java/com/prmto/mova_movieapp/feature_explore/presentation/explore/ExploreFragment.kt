package com.prmto.mova_movieapp.feature_explore.presentation.explore

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.data.models.enums.Category
import com.prmto.mova_movieapp.core.domain.repository.ConnectivityObserver
import com.prmto.mova_movieapp.core.presentation.util.asString
import com.prmto.mova_movieapp.core.util.HandlePagingLoadStates
import com.prmto.mova_movieapp.databinding.FragmentExploreBinding
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.FilterMoviesAdapter
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.FilterTvSeriesAdapter
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.SearchRecyclerAdapter
import com.prmto.mova_movieapp.feature_explore.presentation.event.ExploreFragmentEvent
import com.prmto.mova_movieapp.feature_explore.presentation.event.ExploreUiEvent
import com.prmto.mova_movieapp.feature_explore.presentation.explore.event.ExploreAdapterLoadStateEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ExploreFragment @Inject constructor(

) : Fragment(R.layout.fragment_explore) {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ExploreViewModel

    @Inject
    lateinit var connectivityObserver: ConnectivityObserver

    private val searchRecyclerAdapter: SearchRecyclerAdapter by lazy { SearchRecyclerAdapter() }

    private val movieFilterAdapter: FilterMoviesAdapter by lazy { FilterMoviesAdapter() }

    private val tvFilterAdapter: FilterTvSeriesAdapter by lazy { FilterTvSeriesAdapter() }

    private var movieDiscoverJob: Job? = null
    private var tvDiscoverJob: Job? = null
    private var searchJob: Job? = null
    private var job: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ExploreViewModel::class.java]
        val binding = FragmentExploreBinding.bind(view)
        _binding = binding

        observeConnectivityStatus()
        collectUiEvent()
        setupAdapters()
        addTextChangedListener()
        searchRecyclerAdapterListeners()
        handlePagingLoadStates()
        binding.filter.setOnClickListener {
            findNavController().navigate(ExploreFragmentDirections.actionExploreFragmentToFilterBottomSheetFragment())
        }
        setBtnErrorClickListener()
    }

    private fun observeConnectivityStatus() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                connectivityObserver.observe().collectLatest {
                    if (it == ConnectivityObserver.Status.Avaliable) {
                        job?.cancel()
                        collectData()
                    } else {
                        showErrorScreenAndHideDetailScreen()
                        job?.cancel()
                    }
                }
            }
        }
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
        job = viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.query.collectLatest { query ->
                        binding.edtQuery.setText(query)
                    }
                }

                launch {
                    viewModel.filterBottomSheetState.collectLatest { filterBottomState ->
                        when (filterBottomState.categoryState) {
                            Category.MOVIE -> {
                                viewModel.onEventExploreFragment(ExploreFragmentEvent.RemoveQuery)
                                movieDiscoverJob = launch {
                                    viewModel.discoverMovie().collectLatest {
                                        hideTvAndSearchAdapters()
                                        cancelTvAndSearchJobs()
                                        binding.recyclerDiscoverMovie.visibility = View.VISIBLE
                                        movieFilterAdapter.submitData(it)
                                    }
                                }
                            }

                            Category.TV -> {
                                viewModel.onEventExploreFragment(ExploreFragmentEvent.RemoveQuery)
                                tvDiscoverJob = launch {
                                    viewModel.discoverTv().collectLatest {
                                        cancelMovieAndSearchJobs()
                                        hideMoviesAndSearchAdapter()
                                        binding.recyclerDiscoverTv.visibility = View.VISIBLE
                                        tvFilterAdapter.submitData(it)
                                    }
                                }
                            }

                            Category.SEARCH -> {
                                viewModel.query.collectLatest { query ->
                                    binding.edtQuery.setSelection(query.length)
                                    searchJob = launch {
                                        viewModel.multiSearch(query).collectLatest {
                                            cancelTvAndMovieJobs()
                                            hideTvAndMovieAdapters()
                                            binding.recyclerSearch.visibility = View.VISIBLE
                                            searchRecyclerAdapter.submitData(it)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun collectUiEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.eventFlow.collectLatest { event ->
                        when (event) {
                            is ExploreUiEvent.ShowSnackbar -> {
                                Snackbar.make(
                                    requireView(),
                                    event.uiText.asString(requireContext()),
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                            is ExploreUiEvent.PopBackStack -> {
                                findNavController().popBackStack()
                            }
                            is ExploreUiEvent.NavigateTo -> {
                                findNavController().navigate(event.directions)
                            }
                        }
                    }
                }

                launch {
                    viewModel.pagingState.collectLatest {

                        when (viewModel.filterBottomSheetState.value.categoryState) {
                            Category.MOVIE -> {
                                binding.filterShimmerLayout.isVisible =
                                    it.filterAdapterState.isLoading
                                binding.recyclerDiscoverMovie.isVisible =
                                    !it.filterAdapterState.isLoading
                            }
                            Category.TV -> {
                                binding.filterShimmerLayout.isVisible =
                                    it.filterAdapterState.isLoading
                                binding.recyclerDiscoverTv.isVisible =
                                    !it.filterAdapterState.isLoading
                            }
                            Category.SEARCH -> {
                                binding.filterShimmerLayout.isVisible =
                                    it.searchAdapterState.isLoading
                                binding.recyclerSearch.isVisible =
                                    !it.searchAdapterState.isLoading
                            }
                        }

                        if (it.errorUiText != null) {
                            showErrorScreenAndHideDetailScreen()
                        }

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
        binding.detailScreen.isVisible = false
        binding.errorScreen.isVisible = true
    }

    private fun hideErrorScreenAndShowDetailScreen() {
        binding.detailScreen.isVisible = true
        binding.errorScreen.isVisible = false
    }

    private fun setBtnErrorClickListener() {
        binding.btnError.setOnClickListener {
            hideErrorScreenAndShowDetailScreen()
            when (viewModel.filterBottomSheetState.value.categoryState) {
                Category.MOVIE -> movieFilterAdapter.retry()
                Category.TV -> tvFilterAdapter.retry()
                Category.SEARCH -> searchRecyclerAdapter.retry()
            }
        }
    }

    private fun handlePagingLoadStates() {
        HandlePagingLoadStates(
            pagingAdapter = tvFilterAdapter,
            onLoading = { viewModel.onAdapterLoadStateEvent(ExploreAdapterLoadStateEvent.FilterAdapterLoading) },
            onNotLoading = { viewModel.onAdapterLoadStateEvent(ExploreAdapterLoadStateEvent.FilterAdapterNotLoading) },
            onError = {
                viewModel.onAdapterLoadStateEvent(
                    ExploreAdapterLoadStateEvent.PagingError(
                        it
                    )
                )
            }
        )

        HandlePagingLoadStates(
            pagingAdapter = movieFilterAdapter,
            onLoading = { viewModel.onAdapterLoadStateEvent(ExploreAdapterLoadStateEvent.FilterAdapterLoading) },
            onNotLoading = { viewModel.onAdapterLoadStateEvent(ExploreAdapterLoadStateEvent.FilterAdapterNotLoading) },
            onError = {
                viewModel.onAdapterLoadStateEvent(
                    ExploreAdapterLoadStateEvent.PagingError(
                        it
                    )
                )
            }
        )

        HandlePagingLoadStates<Any>(
            searchPagingAdapter = searchRecyclerAdapter,
            onLoading = { viewModel.onAdapterLoadStateEvent(ExploreAdapterLoadStateEvent.SearchAdapterLoading) },
            onNotLoading = { viewModel.onAdapterLoadStateEvent(ExploreAdapterLoadStateEvent.SearchAdapterNotLoading) },
            onError = {
                viewModel.onAdapterLoadStateEvent(
                    ExploreAdapterLoadStateEvent.PagingError(
                        it
                    )
                )
            }
        )
    }

    private fun searchRecyclerAdapterListeners() {
        val action =
            ExploreFragmentDirections.actionExploreFragmentToDetailBottomSheet(null, null)
        setupSearchRecyclerAdapterListener(action = action)
        movieFilterAdapter.setOnItemClickListener { movie ->
            action.movie = movie
            action.tvSeries = null
            viewModel.onEventExploreFragment(
                ExploreFragmentEvent.NavigateToDetailBottomSheet(
                    action
                )
            )
        }
        tvFilterAdapter.setOnItemClickListener { tvSeries ->
            action.movie = null
            action.tvSeries = tvSeries
            viewModel.onEventExploreFragment(
                ExploreFragmentEvent.NavigateToDetailBottomSheet(
                    action
                )
            )
        }
    }

    private fun setupSearchRecyclerAdapterListener(
        action: ExploreFragmentDirections.ActionExploreFragmentToDetailBottomSheet
    ) {
        searchRecyclerAdapter.setOnTvSearchClickListener { tvSeries ->
            action.movie = null
            action.tvSeries = tvSeries
            viewModel.onEventExploreFragment(
                ExploreFragmentEvent.NavigateToDetailBottomSheet(
                    action
                )
            )
        }
        searchRecyclerAdapter.setOnMovieSearchClickListener { movie ->
            action.movie = movie
            action.tvSeries = null
            viewModel.onEventExploreFragment(
                ExploreFragmentEvent.NavigateToDetailBottomSheet(
                    action
                )
            )
        }
        searchRecyclerAdapter.setOnPersonSearchClickListener { person ->
            val action =
                ExploreFragmentDirections.actionExploreFragmentToPersonDetailFragment(person.id)

            viewModel.onEventExploreFragment(
                ExploreFragmentEvent.NavigateToPersonDetail(
                    action
                )
            )
        }
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
        binding.recyclerDiscoverMovie.visibility = View.GONE
        binding.recyclerSearch.visibility = View.GONE
    }

    private fun hideTvAndSearchAdapters() {
        binding.recyclerSearch.visibility = View.GONE
        binding.recyclerDiscoverTv.visibility = View.GONE
    }

    private fun hideTvAndMovieAdapters() {
        binding.recyclerDiscoverTv.visibility = View.GONE
        binding.recyclerDiscoverMovie.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
