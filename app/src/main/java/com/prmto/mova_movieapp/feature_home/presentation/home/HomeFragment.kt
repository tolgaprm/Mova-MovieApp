package com.prmto.mova_movieapp.feature_home.presentation.home

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prmto.core_domain.util.UiText
import com.prmto.core_domain.util.asString
import com.prmto.core_ui.base.fragment.BaseFragmentWithUiEvent
import com.prmto.core_ui.util.collectFlow
import com.prmto.core_ui.util.collectFlowInside
import com.prmto.core_ui.util.getCountryIsoCode
import com.prmto.core_ui.util.handlePagingLoadState.HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter
import com.prmto.core_ui.util.makeGone
import com.prmto.core_ui.util.makeVisible
import com.prmto.home_ui.adapter.HandlePagingStateNowPlayingRecyclerAdapter
import com.prmto.home_ui.adapter.NowPlayingRecyclerAdapter
import com.prmto.home_ui.adapter.PopularMoviesAdapter
import com.prmto.home_ui.adapter.PopularTvSeriesAdapter
import com.prmto.home_ui.adapter.TopRatedMoviesAdapter
import com.prmto.home_ui.adapter.TopRatedTvSeriesAdapter
import com.prmto.home_ui.databinding.FragmentHomeBinding
import com.prmto.mova_movieapp.feature_home.presentation.event.HomeEvent
import dagger.hilt.android.AndroidEntryPoint
import com.prmto.core_ui.R as CoreUiR
import com.prmto.home_ui.R as HomeUiR

@AndroidEntryPoint
class HomeFragment : BaseFragmentWithUiEvent<FragmentHomeBinding, HomeViewModel>(
    inflater = FragmentHomeBinding::inflate
) {

    private val nowPlayingAdapter: NowPlayingRecyclerAdapter by lazy { NowPlayingRecyclerAdapter() }
    private val popularMoviesAdapter: PopularMoviesAdapter by lazy { PopularMoviesAdapter() }
    private val popularTvSeriesAdapter: PopularTvSeriesAdapter by lazy { PopularTvSeriesAdapter() }
    private val topRatedMoviesAdapter: TopRatedMoviesAdapter by lazy { TopRatedMoviesAdapter() }
    private val topRatedTvSeriesAdapter: TopRatedTvSeriesAdapter by lazy { TopRatedTvSeriesAdapter() }
    override val viewModel: HomeViewModel by viewModels()

    override fun onInitialize() {
        handlePagingLoadStates()
        collectHomeUiEvents()
        updateCountryIsoCode()
        setupRecyclerAdapters()
        addOnBackPressedCallback(addCustomBehavior = this::hideSeeAllPage)
        setupClickListener()
        collectDataLifecycleAware()
    }

    private fun setupClickListener() {
        binding.btnNavigateUp.setOnClickListener {
            hideSeeAllPage()
            binding.seeAllPage.makeGone()
            binding.scrollView.makeVisible()
        }
        binding.btnError.setOnClickListener {
            hideErrorScreenAndShowScrollView()
            nowPlayingAdapter.retry()
            popularMoviesAdapter.retry()
            popularTvSeriesAdapter.retry()
            topRatedMoviesAdapter.retry()
            topRatedTvSeriesAdapter.retry()
        }
        setAdaptersClickListener()
        setupListenerSeeAllClickEvents()
    }

    private fun hideScrollViewAndShowErrorScreen() {
        binding.scrollView.makeGone()
        binding.errorScreen.makeVisible()
    }

    private fun hideErrorScreenAndShowScrollView() {
        binding.errorScreen.makeGone()
        binding.scrollView.makeVisible()
    }

    private fun collectHomeUiEvents() {
        collectFlow(viewModel.consumableViewEvents) { listOfUiEvent ->
            handleUiEvent(
                listOfUiEvent = listOfUiEvent,
                onEventConsumed = viewModel::onEventConsumed
            )
        }
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.homeState.value.isShowsSeeAllPage) {
            val context = requireContext()
            val adapter =
                when (viewModel.homeState.value.seeAllPageToolBarText?.asString(context)) {
                    context.getString(HomeUiR.string.now_playing) -> {
                        nowPlayingAdapter
                    }

                    context.getString(HomeUiR.string.popular_movies) -> {
                        popularMoviesAdapter
                    }

                    context.getString(HomeUiR.string.popular_tv_series) -> {
                        popularTvSeriesAdapter
                    }

                    context.getString(HomeUiR.string.top_rated_movies) -> {
                        topRatedMoviesAdapter
                    }

                    context.getString(HomeUiR.string.top_rated_tv_series) -> {
                        topRatedTvSeriesAdapter
                    }

                    else -> nowPlayingAdapter
                }
            binding.recyclerViewSeeAll.adapter = adapter
        }
    }

    private fun handlePagingLoadStates() {
        HandlePagingStateNowPlayingRecyclerAdapter(
            nowPlayingRecyclerAdapter = nowPlayingAdapter,
            onLoading = binding.nowPlayingShimmerLayout::makeVisible,
            onNotLoading = binding.nowPlayingShimmerLayout::makeGone,
            onError = { hideScrollViewAndShowErrorScreen() }
        )

        HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter(
            pagingAdapter = popularMoviesAdapter,
            onLoading = binding.popularMoviesShimmerLayout::makeVisible,
            onNotLoading = binding.popularMoviesShimmerLayout::makeGone,
            onError = { hideScrollViewAndShowErrorScreen() }
        )

        HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter(
            pagingAdapter = topRatedMoviesAdapter,
            onLoading = binding.topRatedMoviesShimmerLayout::makeVisible,
            onNotLoading = binding.topRatedMoviesShimmerLayout::makeGone,
            onError = { hideScrollViewAndShowErrorScreen() }
        )

        HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter(
            pagingAdapter = popularTvSeriesAdapter,
            onLoading = binding.popularTvSeriesShimmerLayout::makeVisible,
            onNotLoading = binding.popularTvSeriesShimmerLayout::makeGone,
            onError = { hideScrollViewAndShowErrorScreen() }
        )

        HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter(
            pagingAdapter = topRatedTvSeriesAdapter,
            onLoading = binding.topRatedTvSeriesShimmerLayout::makeVisible,
            onNotLoading = binding.topRatedTvSeriesShimmerLayout::makeGone,
            onError = { hideScrollViewAndShowErrorScreen() }
        )
    }

    private fun updateCountryIsoCode() {
        val countryIsoCode = requireContext().getCountryIsoCode().uppercase()
        viewModel.onEvent(HomeEvent.UpdateCountryIsoCode(countryIsoCode))
    }

    private fun collectDataLifecycleAware() {
        collectFlowInside {
            collectFlow(viewModel.homeState) { homeState ->
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
            collectFlow(viewModel.getNowPlayingMovies()) { pagingData ->
                nowPlayingAdapter.submitData(pagingData)
            }
            collectFlow(viewModel.getPopularMovies()) { pagingData ->
                popularMoviesAdapter.submitData(pagingData)
            }
            collectFlow(viewModel.getTopRatedMovies()) { pagingData ->
                topRatedMoviesAdapter.submitData(pagingData)
            }
            collectFlow(viewModel.getPopularTvSeries()) { pagingData ->
                popularTvSeriesAdapter.submitData(pagingData)
            }
            collectFlow(viewModel.getTopRatedTvSeries()) { pagingData ->
                topRatedTvSeriesAdapter.submitData(pagingData)
            }
        }
    }

    private fun showSeeAllPage(uiText: UiText?) {
        binding.apply {
            seeAllPage.animation = slideInLeftAnim()
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

    private fun setupListenerSeeAllClickEvents() {
        binding.apply {
            nowPlayingSeeAll.setOnClickListener {
                viewModel.onEvent(
                    HomeEvent.ClickSeeAllText(UiText.StringResource(HomeUiR.string.now_playing))
                )
                recyclerViewSeeAll.adapter = nowPlayingAdapter
            }

            popularMoviesSeeAll.setOnClickListener {
                viewModel.onEvent(
                    HomeEvent.ClickSeeAllText(UiText.StringResource(HomeUiR.string.popular_movies))
                )
                recyclerViewSeeAll.adapter = popularMoviesAdapter
            }

            popularTvSeeAll.setOnClickListener {
                viewModel.onEvent(
                    HomeEvent.ClickSeeAllText(UiText.StringResource(HomeUiR.string.popular_tv_series))
                )
                recyclerViewSeeAll.adapter = popularTvSeriesAdapter
            }

            topRatedMoviesSeeAll.setOnClickListener {
                viewModel.onEvent(
                    HomeEvent.ClickSeeAllText(UiText.StringResource(HomeUiR.string.top_rated_movies))
                )
                recyclerViewSeeAll.adapter = topRatedMoviesAdapter
            }

            topRatedTvSeriesSeeAll.setOnClickListener {
                viewModel.onEvent(
                    HomeEvent.ClickSeeAllText(UiText.StringResource(HomeUiR.string.top_rated_tv_series))
                )
                recyclerViewSeeAll.adapter = topRatedTvSeriesAdapter
            }
        }

    }

    private fun slideInLeftAnim(): Animation =
        AnimationUtils.loadAnimation(requireContext(), CoreUiR.anim.slide_in_left)

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
            findNavController().navigate(action)
        }

        topRatedMoviesAdapter.setOnItemClickListener { movie ->
            action.movie = movie
            action.tvSeries = null
            findNavController().navigate(action)
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
}