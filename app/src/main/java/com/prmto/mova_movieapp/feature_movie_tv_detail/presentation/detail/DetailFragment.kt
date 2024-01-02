package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail

import android.content.Intent
import android.net.Uri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prmto.core_ui.adapter.MovieAdapter
import com.prmto.core_ui.base.fragment.BaseFragment
import com.prmto.core_ui.util.AlertDialogUtil
import com.prmto.core_ui.util.collectFlow
import com.prmto.core_ui.util.collectFlowInside
import com.prmto.core_ui.util.makeGone
import com.prmto.core_ui.util.makeVisible
import com.prmto.core_ui.util.setAddFavoriteIconByFavoriteState
import com.prmto.core_ui.util.setWatchListIconByWatchState
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.databinding.FragmentDetailBinding
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.helper.BindingDetailHelper
import com.prmto.ui.detail.adapter.DetailActorAdapter
import com.prmto.ui.detail.adapter.VideosAdapter
import com.prmto.ui.detail.event.DetailUiEvent
import com.prmto.ui.detail.isSelectedRecommendationTab
import com.prmto.ui.detail.isSelectedTrailerTab
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import com.prmto.core_ui.R as CoreUiR

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>(
    inflater = FragmentDetailBinding::inflate
) {
    private var job: Job? = null
    private var jobVideos: Job? = null
    private val movieRecommendationAdapter: MovieAdapter by lazy { MovieAdapter() }
    private val detailActorAdapter: DetailActorAdapter by lazy { DetailActorAdapter() }
    private val tvRecommendationAdapter: com.prmto.core_ui.adapter.TvSeriesAdapter by lazy { com.prmto.core_ui.adapter.TvSeriesAdapter() }
    private val videosAdapter: VideosAdapter by lazy {
        VideosAdapter(
            viewLifecycleOwner.lifecycle
        )
    }

    private var bindingDetailHelper: BindingDetailHelper? = null

    override val viewModel: DetailViewModel by viewModels()
    override fun onInitialize() {
        bindingDetailHelper = BindingDetailHelper(
            binding = binding,
            context = requireContext(),
            viewModel = viewModel,
            detailActorAdapter = detailActorAdapter
        )
        binding.recommendationRecyclerView.adapter = movieRecommendationAdapter
        binding.videosRecyclerView.adapter = videosAdapter
        setBtnNavigateUpListener()
        setSwipeRefreshListener()
        setRecommendationsAdapterListener()
        binding.swipeRefreshLayout.isEnabled = false
        addOnBackPressedCallback()
        collectDataLifecycleAware()
    }

    private fun setRecommendationsAdapterListener() {
        movieRecommendationAdapter.setOnclickListener { movie ->
            viewModel.onEvent(
                com.prmto.ui.detail.event.DetailEvent.ClickRecommendationItemClick(
                    movie = movie
                )
            )
        }

        tvRecommendationAdapter.setOnclickListener { tvSeries ->
            viewModel.onEvent(
                com.prmto.ui.detail.event.DetailEvent.ClickRecommendationItemClick(
                    tvSeries = tvSeries
                )
            )
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

    private fun collectDataLifecycleAware() {
        job = collectFlowInside {
            collectDetailState()
            collectUiEvent()
        }
    }

    private fun collectUiEvent() {
        collectFlow(viewModel.consumableViewEvents) { listOfUiEvents ->
            if (listOfUiEvents.isNotEmpty()) {
                when (val uiEvent = listOfUiEvents.first()) {
                    is DetailUiEvent.PopBackStack -> {
                        findNavController().popBackStack()
                        viewModel.onEventConsumed()
                    }

                    is DetailUiEvent.ShowSnackbar -> {
                        binding.swipeRefreshLayout.isEnabled = true
                        showSnackbar(uiText = uiEvent.uiText)
                        viewModel.onEventConsumed()
                    }

                    is DetailUiEvent.IntentToImdbWebSite -> {
                        intentToTmdbWebSite(uiEvent.url)
                        viewModel.onEventConsumed()
                    }

                    is DetailUiEvent.NavigateTo -> {
                        findNavController().navigate(uiEvent.directions)
                        viewModel.onEventConsumed()
                    }

                    is DetailUiEvent.ShowAlertDialog -> {
                        AlertDialogUtil.showAlertDialog(
                            context = requireContext(),
                            title = R.string.sign_in,
                            message = R.string.must_login_able_to_add_in_list,
                            positiveBtnMessage = R.string.sign_in,
                            negativeBtnMessage = CoreUiR.string.cancel,
                            onClickPositiveButton = {
                                findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToLoginFragment())
                            }
                        )
                        viewModel.onEventConsumed()
                    }
                }
            }
        }
    }

    private fun collectVideos(): Job {
        return collectFlow(viewModel.videos) {
            it?.let { videos ->
                binding.videosRecyclerView.isVisible = videos.result.isNotEmpty()
                binding.txtVideoInfo.isVisible = videos.result.isEmpty()
                videosAdapter.submitList(videos.result)
            }
        }
    }

    private fun collectDetailState() {
        collectFlow(viewModel.detailState) { detailState ->
            binding.progressBar.isVisible = detailState.isLoading

            if (detailState.isSelectedTrailerTab()) {
                binding.recommendationRecyclerView.makeGone()
                binding.videosRecyclerView.makeVisible()
                jobVideos = collectVideos()
            } else {
                jobVideos?.cancel()
                binding.videosRecyclerView.makeGone()
                binding.recommendationRecyclerView.makeVisible()
            }

            detailState.tvDetail?.let {
                bindingDetailHelper?.bindTvDetail(detailState.tvDetail)
                if (detailState.isSelectedRecommendationTab()) {
                    binding.recommendationRecyclerView.swapAdapter(tvRecommendationAdapter, true)
                }
            }

            detailState.movieDetail?.let {
                bindingDetailHelper?.bindMovieDetail(detailState.movieDetail)
                if (detailState.isSelectedRecommendationTab()) {
                    binding.recommendationRecyclerView.swapAdapter(movieRecommendationAdapter, true)
                }
            }

            detailState.movieRecommendation?.let { movieRecommendation ->
                movieRecommendationAdapter.submitList(movieRecommendation)
            }

            detailState.tvRecommendation?.let {
                tvRecommendationAdapter.submitList(it)
            }

            binding.btnFavoriteList.setAddFavoriteIconByFavoriteState(isFavorite = detailState.doesAddFavorite)
            binding.btnWatchList.setWatchListIconByWatchState(isAddedWatchList = detailState.doesAddWatchList)

            binding.recommendationShimmerLayout.isVisible = detailState.recommendationLoading
            binding.videosShimmerLayout.isVisible = detailState.videosLoading
        }
    }

    private fun intentToTmdbWebSite(tmdbUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(tmdbUrl)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingDetailHelper = null
    }
}