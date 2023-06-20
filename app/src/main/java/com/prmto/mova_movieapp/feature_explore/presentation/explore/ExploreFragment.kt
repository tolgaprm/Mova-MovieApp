package com.prmto.mova_movieapp.feature_explore.presentation.explore

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.material.snackbar.Snackbar
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.data.models.enums.Category
import com.prmto.mova_movieapp.core.domain.repository.isAvaliable
import com.prmto.mova_movieapp.core.presentation.util.UiEvent
import com.prmto.mova_movieapp.core.presentation.util.asString
import com.prmto.mova_movieapp.core.presentation.util.collectFlow
import com.prmto.mova_movieapp.core.presentation.util.isEmpty
import com.prmto.mova_movieapp.core.util.handlePagingLoadState.HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter
import com.prmto.mova_movieapp.core.util.handlePagingLoadState.HandlePagingStateSearchAdapter
import com.prmto.mova_movieapp.databinding.FragmentExploreBinding
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.FilterMoviesAdapter
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.FilterTvSeriesAdapter
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.SearchRecyclerAdapter
import com.prmto.mova_movieapp.feature_explore.presentation.event.ExploreFragmentEvent
import com.prmto.mova_movieapp.feature_explore.presentation.explore.event.ExploreAdapterLoadStateEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExploreFragment : Fragment(R.layout.fragment_explore) {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ExploreViewModel

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

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

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
                        binding.recyclerDiscoverMovie.visibility = View.VISIBLE
                        movieFilterAdapter.submitData(it)
                    }

                }

                Category.TV -> {
                    viewModel.onEventExploreFragment(ExploreFragmentEvent.RemoveQuery)
                    tvDiscoverJob = collectFlow(viewModel.discoverTv()) {
                        cancelMovieAndSearchJobs()
                        hideMoviesAndSearchAdapter()
                        binding.recyclerDiscoverTv.visibility = View.VISIBLE
                        tvFilterAdapter.submitData(it)
                    }
                }

                Category.SEARCH -> {
                    viewModel.query.collectLatest { query ->
                        binding.edtQuery.setSelection(query.length)
                        searchJob = collectFlow(viewModel.multiSearch(query)) {
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

    private fun collectUiEvent() {
        collectFlow(viewModel.eventFlow) { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    Snackbar.make(
                        requireView(),
                        event.uiText.asString(requireContext()),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                is UiEvent.PopBackStack -> {
                    findNavController().popBackStack()
                }

                is UiEvent.NavigateTo -> {
                    findNavController().navigate(event.directions)
                }
            }
        }

        collectFlow(viewModel.pagingState) {
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
                    binding.recyclerSearch.isVisible = !it.searchAdapterState.isLoading
                }
            }

            if (it.errorUiText != null) {
                showErrorScreenAndHideDetailScreen()
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
            if (viewModel.isNetworkAvaliable()) {
                collectData()
                hideErrorScreenAndShowDetailScreen()
            } else {
                Toast.makeText(
                    requireContext(), getString(R.string.internet_error), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun handlePagingLoadStates() {
        HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter(
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

        HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter(
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

        HandlePagingStateSearchAdapter(
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
        val action = ExploreFragmentDirections.actionExploreFragmentToDetailBottomSheet(null, null)
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
