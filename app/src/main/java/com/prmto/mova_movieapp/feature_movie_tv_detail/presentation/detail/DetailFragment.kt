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
import coil.ImageLoader
import com.google.android.material.snackbar.Snackbar
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.util.asString
import com.prmto.mova_movieapp.databinding.FragmentDetailBinding
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.adapter.DetailActorAdapter
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.event.DetailEvent
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.event.DetailUiEvent
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.helper.BindAttributesDetailFrag
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var job: Job? = null

    private lateinit var bindAttributesDetailFrag: BindAttributesDetailFrag

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var imageLoader: ImageLoader

    private val detailActorAdapter: DetailActorAdapter by lazy { DetailActorAdapter() }

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
            },
            onClickDirectorName = { directorId ->
                viewModel.onEvent(
                    DetailEvent.ClickToDirectorName(directorId = directorId)
                )
            },
            onNavigateUp = {
                findNavController().popBackStack()
            },
            onSwipeRefresh = {
                job?.cancel()
                collectDataLifecycleAware()
                binding.swipeRefreshLayout.isRefreshing = false
            }
        )
        binding.swipeRefreshLayout.isEnabled = false

        addOnBackPressedCallback()

        collectDataLifecycleAware()

        setAdapterListener()
    }

    private fun setAdapterListener() {
        detailActorAdapter.setActorTextListener { actorId ->
            Timber.d(actorId.toString())
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

    private suspend fun collectDetailState() {
        viewModel.detailState.collectLatest { detailState ->
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