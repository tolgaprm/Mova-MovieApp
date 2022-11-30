package com.prmto.mova_movieapp.presentation.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.databinding.FragmentDetailBinding
import com.prmto.mova_movieapp.util.Constants.DETAIL_DEFAULT_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    @Inject
    lateinit var imageLoader: ImageLoader
    private lateinit var bindAttributesDetailFragment: BindAttributesDetailFragment

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val detailArgs by navArgs<DetailFragmentArgs>()
    private val viewModel: DetailViewModel by viewModels()

    private lateinit var detailActorAdapter: DetailActorAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        setupDetailActorAdapter()

        bindAttributesDetailFragment = BindAttributesDetailFragment(
            binding = binding,
            imageLoader = imageLoader,
            context = requireContext()
        ) { tmdbUrl ->
            intentToImdbWebSite(tmdbUrl)
        }

        addOnBackPressedCallback()

        navigateUp()

        setDetailIdToStateSavedHandle()

        collectDataLifecycleAware()
    }

    private fun setupDetailActorAdapter() {
        detailActorAdapter = DetailActorAdapter(imageLoader)
        binding.recyclerViewActor.adapter = detailActorAdapter
    }


    private fun navigateUp() {
        binding.btnNavigateUp.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun addOnBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun setDetailIdToStateSavedHandle() {
        val movieId = detailArgs.movieId
        val tvId = detailArgs.tvId

        if (movieId != DETAIL_DEFAULT_ID) {
            viewModel.setMovieDetailId(movieId)
        } else if (tvId != DETAIL_DEFAULT_ID) {
            viewModel.setTvDetailId(tvId)
        }
    }

    private fun collectDataLifecycleAware() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { collectTvId() }

                launch { collectMovieId() }

                launch { collectDetailState() }
            }
        }
    }

    private suspend fun collectMovieId() {
        viewModel.movieDetailId.collectLatest { movieId ->
            if (movieId != DETAIL_DEFAULT_ID) {
                viewModel.getMovieDetail()
            }
        }
    }

    private suspend fun collectTvId() {
        viewModel.tvDetailId.collectLatest { tvId ->
            if (tvId != DETAIL_DEFAULT_ID) {
                viewModel.getTvDetail()
            }
        }
    }

    private suspend fun collectDetailState() {

        viewModel.detailState.collectLatest { detailState ->
            if (detailState.loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE

                detailState.tvDetail?.let {
                    bindAttributesDetailFragment.bindTvDetail(
                        tvDetail = it
                    )
                    detailActorAdapter.submitList(it.credit.cast)
                }


                detailState.movieDetail?.let { movieDetail ->
                    bindAttributesDetailFragment.bindMovieDetail(
                        movieDetail = movieDetail
                    )
                    detailActorAdapter.submitList(movieDetail.credit.cast)
                }

                if (detailState.errorId != null) {
                    Toast.makeText(
                        requireContext(),
                        requireContext().getString(detailState.errorId),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun intentToImdbWebSite(tmdbUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(tmdbUrl)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}