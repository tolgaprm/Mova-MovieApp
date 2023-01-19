package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.util.asString
import com.prmto.mova_movieapp.core.util.HandlePagingLoadStates
import com.prmto.mova_movieapp.core.util.toolBarTextVisibilityByScrollPositionOfNestedScrollView
import com.prmto.mova_movieapp.databinding.FragmentDetailBinding
import com.prmto.mova_movieapp.feature_home.domain.models.Movie
import com.prmto.mova_movieapp.feature_home.presentation.home.adapter.NowPlayingRecyclerAdapter
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.adapter.DetailActorAdapter
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.adapter.TvRecommendationAdapter
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.adapter.VideosAdapter
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.event.DetailEvent
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.event.DetailLoadStateEvent
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.event.DetailUiEvent
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.helper.BindMovieDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.helper.BindTvDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.util.Constants
import com.prmto.mova_movieapp.feature_movie_tv_detail.util.isSelectedRecommendationTab
import com.prmto.mova_movieapp.feature_movie_tv_detail.util.isSelectedTrailerTab
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var job: Job? = null
    private var jobMovieId: Job? = null
    private var jobTvId: Job? = null
    private var jobVideos: Job? = null

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var imageLoader: ImageLoader

    private val detailActorAdapter: DetailActorAdapter by lazy { DetailActorAdapter() }
    private val movieRecommendationAdapter: NowPlayingRecyclerAdapter by lazy { NowPlayingRecyclerAdapter() }
    private val tvRecommendationAdapter: TvRecommendationAdapter by lazy { TvRecommendationAdapter() }
    private val videosAdapter: VideosAdapter by lazy { VideosAdapter(viewLifecycleOwner.lifecycle) }

    private val viewModel: DetailViewModel by viewModels()

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailBinding.bind(view)
        binding.recommendationRecyclerView.adapter = movieRecommendationAdapter
        binding.videosRecyclerView.adapter = videosAdapter

        setupDetailActorAdapter()

        addTabLayoutListener()

        setBtnNavigateUpListener()

        setAdapterListener()

        setTmdbImageOnClickListener()

        setSwipeRefreshListener()

        setDirectorTextListener()

        binding.swipeRefreshLayout.isEnabled = false

        addOnBackPressedCallback()

        collectDataLifecycleAware()

        toolBarTextVisibilityByScrollPositionOfNestedScrollView(
            nestedScrollView = binding.nestedScrollView,
            position = 1500,
            toolBarTitle = binding.txtToolBarTitle,
            toolbar = binding.toolbar,
            context = requireContext()
        )

        handleTvRecommendationsPagingLoadStates()
        handleMovieRecommendationsPagingLoadStates()
    }

    private fun setBtnNavigateUpListener() {
        binding.btnNavigateUp.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setSwipeRefreshListener() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            job?.cancel()
            collectDataLifecycleAware()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setDirectorTextListener() {
        binding.creatorDirectorLinearLayout.setOnHierarchyChangeListener(
            object : ViewGroup.OnHierarchyChangeListener {
                override fun onChildViewAdded(p0: View?, director: View?) {
                    director?.setOnClickListener {
                        viewModel.onEvent(
                            DetailEvent.ClickToDirectorName(directorId = director.id)
                        )
                    }
                }

                override fun onChildViewRemoved(p0: View?, p1: View?) {
                    return
                }
            }
        )
    }

    private fun setTmdbImageOnClickListener() {
        binding.imvTmdb.setOnClickListener {
            val tmdbUrl = if (!viewModel.isTvIdEmpty()) {
                "${Constants.TMDB_TV_URL}${viewModel.tvIdState.value}"
            } else {
                "${Constants.TMDB_MOVIE_URL}${viewModel.movieIdState.value}"
            }
            viewModel.onEvent(DetailEvent.IntentToImdbWebSite(tmdbUrl))
        }
    }

    private fun addTabLayoutListener() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.recommendationRecyclerView.removeAllViews()
                    viewModel.onEvent(DetailEvent.SelectedTab(tab.position))
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun setAdapterListener() {
        detailActorAdapter.setActorTextListener { actorId ->
            viewModel.onEvent(DetailEvent.ClickActorName(actorId = actorId))
        }
    }

    private fun setupDetailActorAdapter() {
        binding.recyclerViewActor.adapter = detailActorAdapter
    }

    private fun addOnBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.onEvent(DetailEvent.OnBackPressed)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun collectDataLifecycleAware() {
        job = viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    collectDetailState()
                }
                
                launch {
                    viewModel.selectedTabPosition.collectLatest { selectedTabPosition ->
                        jobMovieId = launch {
                            jobVideos?.cancel()
                            collectMovieIdState(selectedTabPosition = selectedTabPosition)
                        }

                        jobTvId = launch {
                            jobVideos?.cancel()
                            collectTvIdState(selectedTabPosition = selectedTabPosition)
                        }

                        jobVideos = launch {
                            collectVideos()
                        }

                        if (selectedTabPosition.isSelectedTrailerTab()) {
                            jobTvId?.cancel()
                            jobMovieId?.cancel()
                            binding.recommendationRecyclerView.isVisible = false
                            binding.videosRecyclerView.isVisible = true
                        } else {
                            jobVideos?.cancel()
                            binding.videosRecyclerView.isVisible = false
                            binding.recommendationRecyclerView.isVisible = true
                        }
                    }
                }

                launch {
                    viewModel.eventUiFlow.collectLatest { uiEvent ->
                        when (uiEvent) {
                            is DetailUiEvent.PopBackStack -> {
                                findNavController().popBackStack()
                            }
                            is DetailUiEvent.ShowSnackbar -> {
                                binding.swipeRefreshLayout.isEnabled = true
                                Snackbar.make(
                                    requireView(),
                                    uiEvent.uiText.asString(requireContext()),
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                            is DetailUiEvent.IntentToImdbWebSite -> {
                                intentToTmdbWebSite(uiEvent.url)
                            }
                            is DetailUiEvent.NavigateTo -> {
                                findNavController().navigate(uiEvent.directions)
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun collectMovieIdState(selectedTabPosition: Int) {
        viewModel.movieIdState.collectLatest { movieId ->
            if (movieId != Constants.DETAIL_DEFAULT_ID && selectedTabPosition.isSelectedRecommendationTab()) {
                collectMovieRecommendationsAndSwapAdapter(movieId = movieId)
            } else {
                jobMovieId?.cancel()
            }
        }
    }

    private suspend fun collectMovieRecommendationsAndSwapAdapter(movieId: Int) {
        binding.recommendationRecyclerView.swapAdapter(movieRecommendationAdapter, true)
        viewModel.getMovieRecommendations(movieId = movieId)
            .collectLatest { pagingData ->
                movieRecommendationAdapter.submitData(pagingData)
            }
    }

    private suspend fun collectTvIdState(selectedTabPosition: Int) {
        viewModel.tvIdState.collectLatest { tvId ->
            if (tvId != Constants.DETAIL_DEFAULT_ID && selectedTabPosition.isSelectedRecommendationTab()) {
                collectTvRecommendationsAndSwapAdapter(tvId = tvId)
            } else {
                jobTvId?.cancel()
            }
        }
    }

    private suspend fun collectTvRecommendationsAndSwapAdapter(tvId: Int) {
        binding.recommendationRecyclerView.swapAdapter(tvRecommendationAdapter, true)
        viewModel.getTvRecommendations(tvId = tvId)
            .collectLatest { pagingData ->
                tvRecommendationAdapter.submitData(pagingData)
            }
    }

    private suspend fun collectVideos() {
        viewModel.videos.collectLatest {
            it?.let { videos ->
                binding.videosRecyclerView.isVisible = videos.result.isNotEmpty()
                binding.txtVideoInfo.isVisible = videos.result.isEmpty()
                videosAdapter.submitList(videos.result)
            }
        }
    }

    private suspend fun collectDetailState() {
        viewModel.detailState.collectLatest { detailState ->
            binding.progressBar.isVisible = detailState.loading
            detailState.tvDetail?.let { tvDetail ->
                BindTvDetail(
                    tvDetail = tvDetail,
                    binding = binding,
                    context = requireContext()
                )
                detailActorAdapter.submitList(tvDetail.credit.cast)
            }

            detailState.movieDetail?.let { movieDetail ->
                BindMovieDetail(
                    movieDetail = movieDetail,
                    binding = binding,
                    context = requireContext()
                )
                detailActorAdapter.submitList(movieDetail.credit.cast)
            }

            binding.recommendationShimmerLayout.isVisible = detailState.recommendationLoading

            binding.videosShimmerLayout.isVisible = detailState.videosLoading
        }
    }

    private fun handleTvRecommendationsPagingLoadStates() {
        HandlePagingLoadStates(
            pagingAdapter = tvRecommendationAdapter,
            onLoading = { viewModel.onAdapterLoadStateEvent(DetailLoadStateEvent.RecommendationLoading) },
            onNotLoading = { viewModel.onAdapterLoadStateEvent(DetailLoadStateEvent.RecommendationNotLoading) },
            onError = { viewModel.onAdapterLoadStateEvent(DetailLoadStateEvent.PagingError(it)) }
        )
    }

    private fun handleMovieRecommendationsPagingLoadStates() {
        HandlePagingLoadStates<Movie>(
            nowPlayingRecyclerAdapter = movieRecommendationAdapter,
            onLoading = { viewModel.onAdapterLoadStateEvent(DetailLoadStateEvent.RecommendationLoading) },
            onNotLoading = { viewModel.onAdapterLoadStateEvent(DetailLoadStateEvent.RecommendationNotLoading) },
            onError = { viewModel.onAdapterLoadStateEvent(DetailLoadStateEvent.PagingError(it)) }
        )
    }

    private fun intentToTmdbWebSite(tmdbUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(tmdbUrl)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}