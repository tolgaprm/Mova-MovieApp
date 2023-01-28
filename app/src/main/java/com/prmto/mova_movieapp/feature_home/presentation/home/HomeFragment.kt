package com.prmto.mova_movieapp.feature_home.presentation.home


import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.domain.repository.isAvaliable
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.presentation.util.asString
import com.prmto.mova_movieapp.core.presentation.util.isEmpty
import com.prmto.mova_movieapp.core.util.HandlePagingLoadStates
import com.prmto.mova_movieapp.core.util.getCountryIsoCode
import com.prmto.mova_movieapp.databinding.FragmentHomeBinding
import com.prmto.mova_movieapp.feature_home.domain.models.Movie
import com.prmto.mova_movieapp.feature_home.presentation.home.adapter.*
import com.prmto.mova_movieapp.feature_home.presentation.home.event.HomeAdapterLoadStateEvent
import com.prmto.mova_movieapp.feature_home.presentation.home.event.HomeEvent
import com.prmto.mova_movieapp.feature_home.presentation.home.event.HomeUiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val nowPlayingAdapter: NowPlayingRecyclerAdapter by lazy { NowPlayingRecyclerAdapter() }

    private val popularMoviesAdapter: PopularMoviesAdapter by lazy { PopularMoviesAdapter() }

    private val popularTvSeriesAdapter: PopularTvSeriesAdapter by lazy { PopularTvSeriesAdapter() }

    private val topRatedMoviesAdapter: TopRatedMoviesAdapter by lazy { TopRatedMoviesAdapter() }

    private val topRatedTvSeriesAdapter: TopRatedTvSeriesAdapter by lazy { TopRatedTvSeriesAdapter() }

    private val viewModel: HomeViewModel by viewModels()

    private var job: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        _binding = binding

        handlePagingLoadStates()
        observeConnectivityStatus()
        collectHomeUiEventsAndLoadState()
        updateCountryIsoCode()
        setupRecyclerAdapters()
        setAdaptersClickListener()
        setupListenerSeeAllClickEvents()
        addOnBackPressedCallback()
        binding.btnNavigateUp.setOnClickListener {
            viewModel.onEvent(HomeEvent.NavigateUpFromSeeAllSection)
        }
        binding.btnError.setOnClickListener {
            if (viewModel.isNetworkAvaliable()) {
                collectDataLifecycleAware()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.internet_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun observeConnectivityStatus() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.networkState.collectLatest { networkState ->
                    if (networkState.isAvaliable()) {
                        job?.cancel()
                        showScrollView()
                        hideErrorScreen()
                        collectDataLifecycleAware()
                    } else {
                        job?.cancel()
                        if (isEmptyAdapters()) {
                            hideScrollView()
                            showErrorScreen()
                        }
                    }
                }
            }
        }
    }

    private fun isEmptyAdapters(): Boolean {
        return nowPlayingAdapter.itemCount <= 0 || popularMoviesAdapter.isEmpty() || popularTvSeriesAdapter.isEmpty() || topRatedMoviesAdapter.isEmpty() || topRatedTvSeriesAdapter.isEmpty()
    }

    private fun showScrollView() {
        binding.scrollView.isVisible = true
    }

    private fun hideScrollView() {
        binding.scrollView.isVisible = false
    }

    private fun showErrorScreen() {
        binding.errorScreen.isVisible = true
    }

    private fun hideErrorScreen() {
        binding.errorScreen.isVisible = false
    }

    private fun collectHomeUiEventsAndLoadState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.eventFlow.collectLatest { uiEvent ->
                        when (uiEvent) {
                            is HomeUiEvent.NavigateTo -> findNavController().navigate(
                                uiEvent.directions
                            )
                            is HomeUiEvent.ShowSnackbar -> {
                                Snackbar.make(
                                    requireView(),
                                    uiEvent.uiText.asString(requireContext()),
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
                launch {
                    viewModel.adapterLoadState.collectLatest {
                        binding.nowPlayingShimmerLayout.isVisible = it.nowPlayingState.isLoading
                        binding.popularMoviesShimmerLayout.isVisible =
                            it.popularMoviesState.isLoading
                        binding.popularTvSeriesShimmerLayout.isVisible =
                            it.popularTvSeriesState.isLoading
                        binding.topRatedMoviesShimmerLayout.isVisible =
                            it.topRatedMoviesState.isLoading
                        binding.topRatedTvSeriesShimmerLayout.isVisible =
                            it.topRatedTvSeriesState.isLoading
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.homeState.value.isShowsSeeAllPage) {
            val context = requireContext()
            val adapter =
                when (viewModel.homeState.value.seeAllPageToolBarText?.asString(context)) {
                    context.getString(R.string.now_playing) -> {
                        nowPlayingAdapter
                    }
                    context.getString(R.string.popular_movies) -> {
                        popularMoviesAdapter
                    }
                    context.getString(R.string.popular_tv_series) -> {
                        popularTvSeriesAdapter
                    }
                    context.getString(R.string.top_rated_movies) -> {
                        topRatedMoviesAdapter
                    }
                    context.getString(R.string.top_rated_tv_series) -> {
                        topRatedTvSeriesAdapter
                    }
                    else -> nowPlayingAdapter
                }
            binding.recyclerViewSeeAll.adapter = adapter
        }
    }

    private fun handlePagingLoadStates() {

        HandlePagingLoadStates<Movie>(
            nowPlayingRecyclerAdapter = nowPlayingAdapter,
            onLoading = { viewModel.onAdapterLoadStateEvent(HomeAdapterLoadStateEvent.NowPlayingLoading) },
            onNotLoading = { viewModel.onAdapterLoadStateEvent(HomeAdapterLoadStateEvent.NowPlayingNotLoading) },
            onError = { eventToPagingError(it) }
        )


        HandlePagingLoadStates(
            pagingAdapter = popularMoviesAdapter,
            onLoading = { viewModel.onAdapterLoadStateEvent(HomeAdapterLoadStateEvent.PopularMoviesLoading) },
            onNotLoading = { viewModel.onAdapterLoadStateEvent(HomeAdapterLoadStateEvent.PopularMoviesNotLoading) },
            onError = { eventToPagingError(it) }
        )

        HandlePagingLoadStates(
            pagingAdapter = topRatedMoviesAdapter,
            onLoading = { viewModel.onAdapterLoadStateEvent(HomeAdapterLoadStateEvent.TopRatedMoviesLoading) },
            onNotLoading = { viewModel.onAdapterLoadStateEvent(HomeAdapterLoadStateEvent.TopRatedMoviesNotLoading) },
            onError = { eventToPagingError(it) }
        )

        HandlePagingLoadStates(
            pagingAdapter = popularTvSeriesAdapter,
            onLoading = { viewModel.onAdapterLoadStateEvent(HomeAdapterLoadStateEvent.PopularTvSeriesLoading) },
            onNotLoading = { viewModel.onAdapterLoadStateEvent(HomeAdapterLoadStateEvent.PopularTvSeriesNotLoading) },
            onError = { eventToPagingError(it) }
        )

        HandlePagingLoadStates(
            pagingAdapter = topRatedTvSeriesAdapter,
            onLoading = { viewModel.onAdapterLoadStateEvent(HomeAdapterLoadStateEvent.TopRatedTvSeriesLoading) },
            onNotLoading = { viewModel.onAdapterLoadStateEvent(HomeAdapterLoadStateEvent.TopRatedTvSeriesNotLoading) },
            onError = { eventToPagingError(it) }
        )

    }

    private fun eventToPagingError(uiText: UiText) {
        viewModel.onAdapterLoadStateEvent(HomeAdapterLoadStateEvent.PagingError(uiText))
    }

    private fun updateCountryIsoCode() {
        val countryIsoCode = requireContext().getCountryIsoCode().uppercase()
        viewModel.onEvent(HomeEvent.UpdateCountryIsoCode(countryIsoCode))
    }

    private fun collectDataLifecycleAware() =
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                job = launch {
                    launch {
                        viewModel.homeState.collectLatest { homeState ->
                            binding.apply {
                                seeAllPage.isVisible = homeState.isShowsSeeAllPage
                                scrollView.isVisible = !homeState.isShowsSeeAllPage
                                if (homeState.isShowsSeeAllPage) {
                                    showSeeAllPage(homeState.seeAllPageToolBarText)
                                } else {
                                    hideSeeAllPage()
                                }
                            }
                        }
                    }

                    launch {
                        viewModel.getNowPlayingMovies().collectLatest { pagingData ->
                            nowPlayingAdapter.submitData(pagingData)
                        }
                    }

                    launch {
                        viewModel.getPopularMovies().collectLatest { pagingData ->
                            popularMoviesAdapter.submitData(pagingData)
                        }
                    }

                    launch {
                        viewModel.getTopRatedMovies().collectLatest { pagingData ->
                            topRatedMoviesAdapter.submitData(pagingData)
                        }
                    }

                    launch {
                        viewModel.getPopularTvSeries().collectLatest { pagingData ->
                            popularTvSeriesAdapter.submitData(pagingData)
                        }
                    }

                    launch {
                        viewModel.getTopRatedTvSeries().collectLatest { pagingData ->
                            topRatedTvSeriesAdapter.submitData(pagingData)
                        }
                    }
                }
            }
        }

    private fun showSeeAllPage(uiText: UiText?) {
        binding.apply {
            seeAllPage.animation = slideInLeftAnim()
            recyclerViewSeeAll.layoutManager = GridLayoutManager(requireContext(), 2)
            uiText?.let {
                toolbarText.text = it.asString(requireContext())
            }
        }
    }

    private fun hideSeeAllPage() {
        binding.apply {
            scrollView.animation = slideInLeftAnim()
            recyclerViewSeeAll.removeAllViews()
        }
    }

    private fun addOnBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.onEvent(HomeEvent.OnBackPressed)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun setupListenerSeeAllClickEvents() {
        binding.apply {

            nowPlayingSeeAll.setOnClickListener {
                viewModel.onEvent(
                    HomeEvent.ClickSeeAllText(UiText.StringResource(R.string.now_playing))
                )
                recyclerViewSeeAll.adapter = nowPlayingAdapter
            }

            popularMoviesSeeAll.setOnClickListener {
                viewModel.onEvent(
                    HomeEvent.ClickSeeAllText(UiText.StringResource(R.string.popular_movies))
                )
                recyclerViewSeeAll.adapter = popularMoviesAdapter
            }

            popularTvSeeAll.setOnClickListener {
                viewModel.onEvent(
                    HomeEvent.ClickSeeAllText(UiText.StringResource(R.string.popular_tv_series))
                )
                recyclerViewSeeAll.adapter = popularTvSeriesAdapter
            }

            topRatedMoviesSeeAll.setOnClickListener {
                viewModel.onEvent(
                    HomeEvent.ClickSeeAllText(UiText.StringResource(R.string.top_rated_movies))
                )
                recyclerViewSeeAll.adapter = topRatedMoviesAdapter
            }

            topRatedTvSeriesSeeAll.setOnClickListener {
                viewModel.onEvent(
                    HomeEvent.ClickSeeAllText(UiText.StringResource(R.string.top_rated_tv_series))
                )
                recyclerViewSeeAll.adapter = topRatedTvSeriesAdapter
            }
        }

    }

    private fun slideInLeftAnim(): Animation =
        AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left)

    private fun setupRecyclerAdapters() {
        binding.apply {
            nowPlayingRecyclerView.adapter = nowPlayingAdapter
            nowPlayingRecyclerView.setAlpha(true)
            popularMoviesRecyclerView.adapter = popularMoviesAdapter
            topRatedMoviesRecyclerView.adapter = topRatedMoviesAdapter
            popularTvSeriesRecyclerView.adapter = popularTvSeriesAdapter
            topRatedTvSeriesRecyclerView.adapter = topRatedTvSeriesAdapter
        }
    }

    private fun setAdaptersClickListener() {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailBottomSheet(null, null)
        popularMoviesAdapter.setOnItemClickListener { movie ->
            action.movie = movie
            action.tvSeries = null
            viewModel.onEvent(HomeEvent.NavigateToDetailBottomSheet(action))
        }

        topRatedMoviesAdapter.setOnItemClickListener { movie ->
            action.movie = movie
            action.tvSeries = null
            viewModel.onEvent(HomeEvent.NavigateToDetailBottomSheet(action))
        }

        nowPlayingAdapter.setOnClickListener { movie ->
            action.movie = movie
            action.tvSeries = null
            viewModel.onEvent(HomeEvent.NavigateToDetailBottomSheet(action))
        }

        popularTvSeriesAdapter.setOnItemClickListener { tvSeries ->
            action.tvSeries = tvSeries
            action.movie = null
            viewModel.onEvent(HomeEvent.NavigateToDetailBottomSheet(action))
        }

        topRatedTvSeriesAdapter.setOnItemClickListener { tvSeries ->
            action.tvSeries = tvSeries
            action.movie = null
            viewModel.onEvent(HomeEvent.NavigateToDetailBottomSheet(action))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}