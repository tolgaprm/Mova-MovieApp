package com.prmto.mova_movieapp.presentation.detail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
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
import coil.load
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.data.remote.ImageApi
import com.prmto.mova_movieapp.databinding.FragmentDetailBinding
import com.prmto.mova_movieapp.domain.models.MovieDetail
import com.prmto.mova_movieapp.domain.models.TvDetail
import com.prmto.mova_movieapp.presentation.util.HandleUtils
import com.prmto.mova_movieapp.util.Constants.DETAIL_DEFAULT_ID
import com.prmto.mova_movieapp.util.Constants.TV_SERIES_STATUS_ENDED
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailArgs by navArgs<DetailFragmentArgs>()

    @Inject
    lateinit var imageLoader: ImageLoader

    private val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)


        addOnBackPressedCallback()

        navigateUp()

        setDetailIdToStateSavedHandle()

        collectDataLifecycleAware()

    }


    private fun bindAttributes(
        tvDetail: TvDetail? = null,
        movieDetail: MovieDetail? = null
    ) {
        var posterPath: String? = null
        var filmName = ""
        var voteAverage = 0.0
        var ratingBarText = 0f
        var voteCount = ""
        var genres = ""
        var releaseDate = ""
        var overview = ""

        tvDetail?.let {
            posterPath = tvDetail.posterPath
            filmName = tvDetail.name
            ratingBarText = ((tvDetail.voteAverage * 5) / 10).toFloat()
            voteAverage = tvDetail.voteAverage
            voteCount = HandleUtils.handleVoteCount(tvDetail.voteCount)
            genres = HandleUtils.handleGenreText(tvDetail.genres)
            overview = tvDetail.overview
            val firstAirDate = HandleUtils.handleReleaseDate(tvDetail.firstAirDate)
            releaseDate = if (tvDetail.status == TV_SERIES_STATUS_ENDED) {
                val lastAirDate = HandleUtils.handleReleaseDate(tvDetail.lastAirDate)
                "${firstAirDate}-${lastAirDate}"
            } else {
                "$firstAirDate-"
            }
            binding.txtRuntime.visibility = View.GONE
            binding.imvClockIcon.visibility = View.GONE
            binding.txtSeason.visibility = View.VISIBLE
            binding.imvCircle.visibility = View.VISIBLE

            binding.txtSeason.text = requireContext().getString(
                R.string.season_count,
                tvDetail.numberOfSeasons.toString()
            )

        }

        movieDetail?.let {
            posterPath = movieDetail.posterPath
            filmName = movieDetail.title
            ratingBarText = ((movieDetail.voteAverage * 5) / 10).toFloat()
            voteAverage = movieDetail.voteAverage
            voteCount = HandleUtils.handleVoteCount(movieDetail.voteCount)
            genres = HandleUtils.handleGenreText(movieDetail.genres)
            releaseDate = movieDetail.releaseDate
            overview = movieDetail.overview ?: ""

            movieDetail.runtime?.let { totalMinutes ->
                val hour = totalMinutes / 60
                val minutes = (totalMinutes % 60)

                binding.txtRuntime.text = requireContext().getString(
                    R.string.runtime,
                    hour.toString(),
                    minutes.toString()
                )
            }

            binding.txtRuntime.visibility = View.VISIBLE
            binding.imvClockIcon.visibility = View.VISIBLE
            binding.txtSeason.visibility = View.GONE
            binding.imvCircle.visibility = View.GONE
        }

        binding.apply {
            imvPoster.load(
                ImageApi.getImage(
                    imageUrl = posterPath
                ),
                imageLoader = imageLoader
            ) {
                listener(
                    onStart = {
                        imvPoster.scaleType = ImageView.ScaleType.CENTER
                    },
                    onSuccess = { request, metadata ->
                        imvPoster.scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                )
                placeholder(R.drawable.loading_animate)
            }


            txtMovieName.text = filmName
            ratingBar.rating = ratingBarText

            txtVoteAverageCount.text = requireContext().getString(
                R.string.voteAverageDetail,
                voteAverage.toString().subSequence(0, 3),
                voteCount
            )

            binding.txtGenres.text = genres
            binding.txtReleaseDate.text = releaseDate
            binding.txtOverview.text = overview

        }
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
                launch {
                    viewModel.tvDetailId.collectLatest { tvId ->
                        if (tvId != DETAIL_DEFAULT_ID) {
                            viewModel.getTvDetail()
                        }
                    }
                }

                launch {
                    viewModel.movieDetailId.collectLatest { movieId ->
                        if (movieId != DETAIL_DEFAULT_ID) {
                            viewModel.getMovieDetail()
                        }
                    }
                }

                launch {
                    viewModel.detailState.collectLatest { detailState ->
                        if (detailState.loading) {
                            binding.progressBar.visibility = View.VISIBLE
                        } else {
                            binding.progressBar.visibility = View.GONE
                            if (detailState.tvDetail != null) {
                                bindAttributes(tvDetail = detailState.tvDetail)
                            }

                            if (detailState.movieDetail != null) {
                                bindAttributes(movieDetail = detailState.movieDetail)
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
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}