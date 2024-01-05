package com.prmto.ui.detail.helper

import android.content.Context
import com.prmto.core_ui.util.makeGone
import com.prmto.core_ui.util.makeVisible
import com.prmto.domain.models.credit.Crew
import com.prmto.domain.models.detail.movie.MovieDetail
import com.prmto.domain.util.Constants
import com.prmto.ui.R
import com.prmto.ui.databinding.FragmentDetailBinding
import com.prmto.ui.detail.helper.inflater.DirectorTextInflater

class BindMovieDetail(
    binding: FragmentDetailBinding,
    movieDetail: MovieDetail,
    context: Context
) : BindAttributesDetailFragment(binding = binding, context = context) {

    private val directorTextInflater by lazy {
        DirectorTextInflater(
            context = context,
            viewGroup = binding.creatorDirectorLinearLayout
        )
    }

    init {
        bindImage(posterPath = movieDetail.posterPath)
        removeDirectorsInLayout()
        bindFilmName(filmName = movieDetail.title)
        bindOverview(overview = movieDetail.overview)
        bindWatchProviders(watchProviderItem = movieDetail.watchProviders)
        bindToolBarTitle(toolbarTitle = movieDetail.title)
        bindDetailInfoSection(
            voteAverage = movieDetail.voteAverage,
            formattedVoteCount = movieDetail.formattedVoteCount,
            ratingBarValue = movieDetail.ratingValue,
            genresBySeparatedByComma = movieDetail.genresBySeparatedByComma
        )
        bindMovieRuntime(convertedRuntime = movieDetail.convertedRuntime)
        bindDirectorName(crews = movieDetail.credit?.crew)
        bindReleaseDate(releaseDate = movieDetail.releaseDate)
        hideSeasonText()
        showRuntimeTextAndClockIcon()
    }


    private fun hideSeasonText() {
        binding.apply {
            imvCircle.makeGone()
            txtSeason.makeGone()
        }
    }

    private fun showRuntimeTextAndClockIcon() {
        binding.txtRuntime.makeVisible()
        binding.imvClockIcon.makeVisible()
    }

    private fun bindMovieRuntime(convertedRuntime: Map<String, String>) {
        if (convertedRuntime.isNotEmpty()) {
            binding.txtRuntime.text = context.getString(
                R.string.runtime,
                convertedRuntime[Constants.HOUR_KEY],
                convertedRuntime[Constants.MINUTES_KEY]
            )
        }
    }

    private fun bindDirectorName(crews: List<Crew>?) {
        if (!crews.isNullOrEmpty()) {
            binding.txtDirectorOrCreatorName.text = context.getString(R.string.director_title)
        }

        directorTextInflater.createDirectorText(crews = crews)
    }
}