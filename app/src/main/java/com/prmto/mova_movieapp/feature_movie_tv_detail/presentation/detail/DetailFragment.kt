package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.adapter.MovieAdapter
import com.prmto.mova_movieapp.core.presentation.adapter.TvSeriesAdapter
import com.prmto.mova_movieapp.core.presentation.util.AlertDialogUtil
import com.prmto.mova_movieapp.core.presentation.util.UtilFunctions
import com.prmto.mova_movieapp.core.presentation.util.addOnBackPressedCallback
import com.prmto.mova_movieapp.core.presentation.util.asString
import com.prmto.mova_movieapp.core.presentation.util.collectFlow
import com.prmto.mova_movieapp.core.presentation.util.collectFlowInside
import com.prmto.mova_movieapp.core.util.toolBarTextVisibilityByScrollPositionOfNestedScrollView
import com.prmto.mova_movieapp.databinding.FragmentDetailBinding
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.movie.model.MovieDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.tv.model.TvDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.adapter.DetailActorAdapter
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.adapter.VideosAdapter
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.event.DetailEvent
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.event.DetailUiEvent
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.helper.BindMovieDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.helper.BindTvDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.util.Constants
import com.prmto.mova_movieapp.feature_movie_tv_detail.util.isSelectedRecommendationTab
import com.prmto.mova_movieapp.feature_movie_tv_detail.util.isSelectedTrailerTab
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var job: Job? = null
    private var jobMovieId: Job? = null
    private var jobTvId: Job? = null
    private var jobVideos: Job? = null

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var utilFunctions: UtilFunctions

    private val movieRecommendationAdapter: MovieAdapter by lazy { MovieAdapter() }
    private val detailActorAdapter: DetailActorAdapter by lazy { DetailActorAdapter() }
    private val tvRecommendationAdapter: TvSeriesAdapter by lazy { TvSeriesAdapter() }
    private val videosAdapter: VideosAdapter by lazy { VideosAdapter(viewLifecycleOwner.lifecycle) }

    private val viewModel: DetailViewModel by viewModels()

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailBinding.bind(view)

        utilFunctions = UtilFunctions()
        binding.recommendationRecyclerView.adapter = movieRecommendationAdapter
        binding.videosRecyclerView.adapter = videosAdapter

        setupDetailActorAdapter()

        addTabLayoutListener()

        setBtnNavigateUpListener()

        setAdapterListener()

        setTmdbImageOnClickListener()

        setSwipeRefreshListener()

        setDirectorTextListener()

        binding.btnFavoriteList.setOnClickListener {
            viewModel.onEvent(DetailEvent.ClickedAddFavoriteList)
        }

        binding.btnWatchList.setOnClickListener {
            viewModel.onEvent(DetailEvent.ClickedAddWatchList)
        }

        setRecommendationsAdapterListener()

        binding.swipeRefreshLayout.isEnabled = false

        addOnBackPressedCallback(
            activity = requireActivity(),
            onBackPressed = {
                viewModel.onEvent(DetailEvent.OnBackPressed)
            }
        )

        collectDataLifecycleAware()

        toolBarTextVisibilityByScrollPositionOfNestedScrollView(
            nestedScrollView = binding.nestedScrollView,
            position = 1500,
            toolBarTitle = binding.txtToolBarTitle,
            toolbar = binding.toolbar,
            context = requireContext()
        )
    }

    private fun setRecommendationsAdapterListener() {
        movieRecommendationAdapter.setOnclickListener { movie ->
            viewModel.onEvent(DetailEvent.ClickRecommendationItemClick(movie = movie))
        }

        tvRecommendationAdapter.setOnclickListener { tvSeries ->
            viewModel.onEvent(DetailEvent.ClickRecommendationItemClick(tvSeries = tvSeries))
        }
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

    private fun collectDataLifecycleAware() {
        job = collectFlowInside {
            collectDetailState()
            collectSelectedTabPosition()
            collectUiEventFlow()
        }
    }

    private fun collectUiEventFlow() {
        collectFlow(viewModel.eventUiFlow) { uiEvent ->
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

                is DetailUiEvent.ShowAlertDialog -> {
                    AlertDialogUtil.showAlertDialog(
                        context = requireContext(),
                        title = R.string.sign_in,
                        message = R.string.must_login_able_to_add_in_list,
                        positiveBtnMessage = R.string.sign_in,
                        negativeBtnMessage = R.string.cancel,
                        onClickPositiveButton = {
                            findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToLoginFragment())
                        }
                    )
                }
            }
        }
    }

    private fun collectMovieIdState(selectedTabPosition: Int): Job? {
        jobVideos?.cancel()
        return collectFlow(viewModel.movieIdState) { movieId ->
            if (movieId != Constants.DETAIL_DEFAULT_ID && selectedTabPosition.isSelectedRecommendationTab()) {
                binding.recommendationRecyclerView.swapAdapter(movieRecommendationAdapter, true)
            } else {
                jobMovieId?.cancel()
            }
        }
    }

    private fun collectTvIdState(selectedTabPosition: Int): Job? {
        jobVideos?.cancel()
        return collectFlow(viewModel.tvIdState) { tvId ->
            if (tvId != Constants.DETAIL_DEFAULT_ID && selectedTabPosition.isSelectedRecommendationTab()) {
                binding.recommendationRecyclerView.swapAdapter(tvRecommendationAdapter, true)
            } else {
                jobTvId?.cancel()
            }
        }
    }

    private fun collectVideos(): Job? {
        return collectFlow(viewModel.videos) {
            it?.let { videos ->
                binding.videosRecyclerView.isVisible = videos.result.isNotEmpty()
                binding.txtVideoInfo.isVisible = videos.result.isEmpty()
                videosAdapter.submitList(videos.result)
            }
        }
    }

    private fun collectSelectedTabPosition() {
        collectFlow(viewModel.selectedTabPosition) { selectedTabPosition ->
            jobMovieId = collectMovieIdState(selectedTabPosition = selectedTabPosition)

            jobTvId = collectTvIdState(selectedTabPosition = selectedTabPosition)

            jobVideos = collectVideos()

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

    private fun collectDetailState() {
        collectFlow(viewModel.detailState) { detailState ->
            binding.progressBar.isVisible = detailState.isLoading

            bindTvDetailAndSubmitCast(tvDetail = detailState.tvDetail)

            bindMoviesAndSubmitCast(movieDetail = detailState.movieDetail)

            detailState.movieRecommendation?.let { movieRecommendation ->
                movieRecommendationAdapter.submitList(movieRecommendation)
            }

            detailState.tvRecommendation?.let {
                tvRecommendationAdapter.submitList(it)
            }

            utilFunctions.setAddFavoriteIcon(
                doesAddFavorite = detailState.doesAddFavorite,
                imageButton = binding.btnFavoriteList
            )

            utilFunctions.setAddWatchListIcon(
                doesAddWatchList = detailState.doesAddWatchList,
                imageButton = binding.btnWatchList
            )

            binding.recommendationShimmerLayout.isVisible = detailState.recommendationLoading

            binding.videosShimmerLayout.isVisible = detailState.videosLoading
        }
    }

    private fun intentToTmdbWebSite(tmdbUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(tmdbUrl)
        startActivity(intent)
    }

    private fun bindTvDetailAndSubmitCast(tvDetail: TvDetail?) {
        tvDetail?.let { _ ->
            BindTvDetail(
                tvDetail = tvDetail,
                binding = binding,
                context = requireContext()
            )
            detailActorAdapter.submitList(tvDetail.credit?.cast)
        }
    }

    private fun bindMoviesAndSubmitCast(movieDetail: MovieDetail?) {
        movieDetail?.let { _ ->
            BindMovieDetail(
                movieDetail = movieDetail,
                binding = binding,
                context = requireContext()
            )
            detailActorAdapter.submitList(movieDetail.credit?.cast)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}