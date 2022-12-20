package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import com.google.android.material.snackbar.Snackbar
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.util.asString
import com.prmto.mova_movieapp.core.util.Constants.DETAIL_DEFAULT_ID
import com.prmto.mova_movieapp.databinding.FragmentDetailBinding
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.adapter.DetailActorAdapter
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.event.DetailEvent
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.event.DetailUiEvent
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.helper.BindAttributesDetailFrag
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var job: Job? = null

    private lateinit var bindAttributesDetailFrag: BindAttributesDetailFrag

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var detailActorAdapter: DetailActorAdapter


    private val detailArgs by navArgs<DetailFragmentArgs>()
    private val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailBinding.bind(view)
        setupDetailActorAdapter()

        bindAttributesDetailFrag = BindAttributesDetailFrag(
            binding = binding,
            imageLoader = imageLoader,
            context = requireContext(),
            onClickTmdbImage = { tmdbUrl ->
                viewModel.onEvent(DetailEvent.IntentToImdbWebSite(tmdbUrl))
            }
        )

        binding.btnNavigateUp.setOnClickListener {
            viewModel.onEvent(DetailEvent.OnBackPressed)
        }

        addOnBackPressedCallback()

        setDetailIdToViewModel()

        collectDataLifecycleAware()

        binding.swipeRefreshLayout.setOnRefreshListener {
            job?.cancel()
            collectDataLifecycleAware()
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

    private fun setDetailIdToViewModel() {
        val movieId = detailArgs.movieId
        val tvId = detailArgs.tvId

        if (movieId != DETAIL_DEFAULT_ID) {
            viewModel.onEvent(DetailEvent.UpdateMovieId(movieId))
        } else if (tvId != DETAIL_DEFAULT_ID) {
            viewModel.onEvent(DetailEvent.UpdateTvSeriesId(tvId))
        }
    }

    private fun collectDataLifecycleAware() {
        job = viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    collectDetailState()
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
                        }
                    }
                }
            }
        }
    }

    private suspend fun collectDetailState() {
        viewModel.detailState.collectLatest { detailState ->
            if (detailState.tvId != DETAIL_DEFAULT_ID) {
                viewModel.getTvDetail()
            } else if (detailState.movieId != DETAIL_DEFAULT_ID) {
                viewModel.getMovieDetail()
            }

            binding.progressBar.isVisible = detailState.loading

            detailState.tvDetail?.let {
                bindAttributesDetailFrag.bindTvDetail(
                    tvDetail = it
                )
                detailActorAdapter.submitList(it.credit.cast)
            }

            detailState.movieDetail?.let { movieDetail ->
                bindAttributesDetailFrag.bindMovieDetail(
                    movieDetail = movieDetail
                )
                detailActorAdapter.submitList(movieDetail.credit.cast)
            }
        }
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