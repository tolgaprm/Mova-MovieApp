package com.prmto.mova_movieapp.core.presentation.detail_bottom_sheet

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.data.data_source.remote.ImageApi
import com.prmto.mova_movieapp.core.data.data_source.remote.ImageSize
import com.prmto.mova_movieapp.databinding.FragmentDetailBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentDetailBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val arguments: DetailBottomSheetArgs by navArgs()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailBottomSheetBinding.inflate(inflater, container, false)
        _binding = binding

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments.movie
        val tvSeries = arguments.tvSeries

        setupButtonClickListeners()

        binding.apply {
            if (movie != null) {
                tvName.text = if (movie.title == movie.originalTitle) {
                    movie.title
                } else {
                    getString(
                        R.string.tv_name_with_original_name,
                        movie.title,
                        movie.originalTitle
                    )
                }
                tvReleaseDate.text = movie.releaseDate
                tvOverview.text = movie.overview
                if (movie.posterPath != null) {
                    loadImage(posterPath = movie.posterPath)
                }
                tvBottomInfoText.text =
                    requireContext().getString(R.string.detail_bottom_sheet_movie_info)

                detailSection.setOnClickListener {
                    navigateToDetailFragment(movie.id)
                }
            }

            if (tvSeries != null) {

                tvName.text = if (tvSeries.name == tvSeries.originalName) {
                    tvSeries.name
                } else {
                    getString(
                        R.string.tv_name_with_original_name,
                        tvSeries.name,
                        tvSeries.originalName
                    )
                }
                tvOverview.text = tvSeries.overview
                tvReleaseDate.text = tvSeries.firstAirDate
                if (tvSeries.posterPath != null) {
                    loadImage(posterPath = tvSeries.posterPath)
                }
                tvBottomInfoText.text =
                    requireContext().getString(R.string.detail_bottom_sheet_tv_info)


                detailSection.setOnClickListener {
                    navigateToDetailFragment(tvId = tvSeries.id)
                }

            }
            tvOverview.movementMethod = ScrollingMovementMethod()
        }


    }

    private fun navigateToDetailFragment(movieId: Int? = null, tvId: Int? = null) {
        val action = DetailBottomSheetDirections.actionDetailBottomSheetToDetailFragment()

        movieId?.let {
            action.movieId = movieId
            action.tvId = 0
        }
        tvId?.let {
            action.tvId = tvId
            action.movieId = 0
        }

        findNavController().navigate(action)
    }


    private fun loadImage(posterPath: String) {
        binding.let {
            it.ivPoster.load(
                ImageApi.getImage(
                    imageSize = ImageSize.W185.path,
                    imageUrl = posterPath
                )
            ) {
                imageLoader
            }
        }
    }


    private fun setupButtonClickListeners() {
        binding.apply {
            ibClose.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}