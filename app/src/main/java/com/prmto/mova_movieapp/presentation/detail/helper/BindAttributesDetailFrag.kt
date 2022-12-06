package com.prmto.mova_movieapp.presentation.detail.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import coil.ImageLoader
import coil.load
import com.google.android.material.textview.MaterialTextView
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.data.models.watch_provider.WatchProviderRegion
import com.prmto.mova_movieapp.data.remote.ImageApi
import com.prmto.mova_movieapp.databinding.FragmentDetailBinding
import com.prmto.mova_movieapp.domain.models.credit.Crew
import com.prmto.mova_movieapp.domain.models.detail.CreatedBy
import com.prmto.mova_movieapp.domain.models.detail.MovieDetail
import com.prmto.mova_movieapp.domain.models.detail.TvDetail
import com.prmto.mova_movieapp.presentation.util.HandleUtils
import com.prmto.mova_movieapp.util.Constants
import com.prmto.mova_movieapp.util.Constants.HOUR_KEY
import com.prmto.mova_movieapp.util.Constants.MINUTES_KEY

class BindAttributesDetailFrag(
    val binding: FragmentDetailBinding,
    val imageLoader: ImageLoader,
    val context: Context,
    val onClickTmdbImage: (tmdbUrl: String) -> Unit
) {
    private var isTvDetail = false
    private var currentTvId = 0
    private var currentMovieId = 0

    private val DIRECTION_DEPARTMENT_NAME = "Directing"

    init {
        setTmdbImageOnClickListener()
    }

    fun bindMovieDetail(movieDetail: MovieDetail) {
        isTvDetail = false
        currentMovieId = movieDetail.id
        bindImage(posterPath = movieDetail.posterPath)
        bindMovieName(movieName = movieDetail.title)
        bindDetailInfoSection(
            voteAverage = movieDetail.voteAverage,
            voteCount = movieDetail.voteCount,
            genreList = movieDetail.genres,
            ratingBarValue = movieDetail.ratingValue
        )
        hideSeasonText()
        showRuntimeTextAndClockIcon()
        binding.txtReleaseDate.text = movieDetail.releaseDate
        bindMovieRuntime(convertedRuntime = movieDetail.convertedRuntime)
        bindOverview(overview = movieDetail.overview)
        binding.creatorDirectorLinearLayout.removeViewsInLayout(
            1,
            binding.creatorDirectorLinearLayout.childCount - 1
        )
        bindDirectorName(movieDetail.credit.crew)
        bindWatchProviders(movieDetail.watchProviders.results)
    }

    private fun bindWatchProviders(providerRegion: WatchProviderRegion?) {
        providerRegion?.let { it ->
            val streamLogoPath = it.tr?.flatRate?.first()?.logoPath
            val buyLogoPath = it.tr?.buy?.first()?.logoPath
            val rentLogoPath = it.tr?.rent?.first()?.logoPath

            streamLogoPath?.let {
                binding.imvStream.load(
                    ImageApi.getImage(imageUrl = it),
                    imageLoader = imageLoader
                )
            }

            buyLogoPath?.let {
                binding.imvBuy.load(
                    ImageApi.getImage(imageUrl = it),
                    imageLoader = imageLoader
                )
            }

            rentLogoPath?.let {
                binding.imvRent.load(
                    ImageApi.getImage(imageUrl = it),
                    imageLoader = imageLoader
                )
            }
        }
    }

    private fun bindDirectorName(crews: List<Crew>) {
        val director = crews.find {
            it.department == DIRECTION_DEPARTMENT_NAME
        }
        director?.let {
            binding.txtDirectorOrCreatorName.text = context.getString(R.string.director_title)
            val directorText = LayoutInflater.from(context)
                .inflate(
                    R.layout.creator_text,
                    binding.creatorDirectorLinearLayout,
                    false
                ) as MaterialTextView

            directorText.text = director.name
            directorText.id = director.id
            binding.creatorDirectorLinearLayout.addView(directorText)
        }
    }

    fun bindTvDetail(tvDetail: TvDetail) {
        isTvDetail = true
        currentTvId = tvDetail.id
        bindImage(posterPath = tvDetail.posterPath)
        bindMovieName(movieName = tvDetail.name)
        bindDetailInfoSection(
            voteAverage = tvDetail.voteAverage,
            voteCount = tvDetail.voteCount,
            genreList = tvDetail.genres,
            ratingBarValue = tvDetail.ratingValue
        )
        showSeasonText(season = tvDetail.numberOfSeasons)
        hideRuntimeTextAndClockIcon()
        bindOverview(overview = tvDetail.overview)
        binding.creatorDirectorLinearLayout.removeViewsInLayout(
            1,
            binding.creatorDirectorLinearLayout.childCount - 1
        )
        bindCreatorNames(tvDetail.createdBy)
        binding.txtReleaseDate.text = tvDetail.releaseDate
    }


    private fun bindCreatorNames(createdBy: List<CreatedBy>) {
        if (createdBy.isEmpty()) {
            binding.creatorDirectorLinearLayout.removeAllViews()
            return
        }
        setCreatorNameByCountOfCreator(creatorCount = createdBy.count())
        createdBy.forEach { creator ->
            val creatorText = LayoutInflater.from(context)
                .inflate(
                    R.layout.creator_text,
                    binding.creatorDirectorLinearLayout,
                    false
                ) as MaterialTextView
            creatorText.text = creator.name
            creatorText.id = creator.id
            binding.creatorDirectorLinearLayout.addView(creatorText)
        }
    }

    private fun setCreatorNameByCountOfCreator(creatorCount: Int) {
        binding.txtDirectorOrCreatorName.text = if (creatorCount > 1) {
            context.getString(R.string.plural_creator_title)
        } else {
            context.getString(R.string.singular_creator_title)
        }
    }

    private fun bindImage(posterPath: String?) {
        binding.imvPoster.load(
            ImageApi.getImage(
                imageUrl = posterPath
            ),
            imageLoader = imageLoader
        ) {
            listener(
                onStart = {
                    binding.imvPoster.scaleType = ImageView.ScaleType.CENTER
                },
                onSuccess = { _, _ ->
                    binding.imvPoster.scaleType = ImageView.ScaleType.CENTER_CROP
                }
            )
            placeholder(R.drawable.loading_animate)

        }
    }

    private fun bindMovieName(movieName: String) {
        binding.txtMovieName.text = movieName
    }

    private fun bindOverview(overview: String?) {
        overview?.let {
            binding.txtOverview.text = overview
        }
    }

    private fun bindDetailInfoSection(
        voteAverage: Double,
        voteCount: Int,
        ratingBarValue: Float,
        genreList: List<com.prmto.mova_movieapp.data.models.Genre>
    ) {
        val voteCountText = HandleUtils.convertingVoteCountToString(voteCount = voteCount)
        binding.apply {
            ratingBar.rating = ratingBarValue
            txtGenres.text =
                HandleUtils.convertGenreListToStringSeparatedByCommas(genreList = genreList)
            txtVoteAverageCount.text = context.getString(
                R.string.voteAverageDetail,
                voteAverage.toString().subSequence(0, 3),
                voteCountText
            )
        }
    }

    private fun bindMovieRuntime(convertedRuntime: Map<String, String>) {
        if (convertedRuntime.isNotEmpty()) {
            binding.txtRuntime.text = context.getString(
                R.string.runtime,
                convertedRuntime[HOUR_KEY],
                convertedRuntime[MINUTES_KEY]
            )
        }
    }

    private fun showSeasonText(season: Int) {
        binding.apply {
            imvCircle.visibility = View.VISIBLE
            txtSeason.visibility = View.VISIBLE
            txtSeason.text = context.getString(
                R.string.season_count,
                season.toString()
            )
        }
    }

    private fun hideSeasonText() {
        binding.apply {
            imvCircle.visibility = View.GONE
            txtSeason.visibility = View.GONE
        }
    }

    private fun showRuntimeTextAndClockIcon() {
        binding.txtRuntime.visibility = View.VISIBLE
        binding.imvClockIcon.visibility = View.VISIBLE
    }

    private fun hideRuntimeTextAndClockIcon() {
        binding.txtRuntime.visibility = View.GONE
        binding.imvClockIcon.visibility = View.GONE
    }

    private fun setTmdbImageOnClickListener() {
        binding.imvTmdb.setOnClickListener {
            val tmdbUrl = if (isTvDetail) {
                "${Constants.TMDB_TV_URL}$currentTvId"
            } else {
                "${Constants.TMDB_MOVIE_URL}$currentMovieId"
            }
            onClickTmdbImage(tmdbUrl)
        }
    }
}