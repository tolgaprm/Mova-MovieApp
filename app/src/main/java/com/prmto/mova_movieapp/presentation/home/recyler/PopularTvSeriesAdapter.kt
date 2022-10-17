package com.prmto.mova_movieapp.presentation.home.recyler

import android.content.Context
import coil.ImageLoader
import coil.load
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.data.remote.ImageApi
import com.prmto.mova_movieapp.data.remote.ImageSize
import com.prmto.mova_movieapp.databinding.MovieRowBinding
import com.prmto.mova_movieapp.domain.models.Genre
import com.prmto.mova_movieapp.domain.models.TvSeries
import com.prmto.mova_movieapp.presentation.util.BaseMovieAndTvRecyclerAdapter
import com.prmto.mova_movieapp.presentation.util.Util
import javax.inject.Inject

class PopularTvSeriesAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) : BaseMovieAndTvRecyclerAdapter<TvSeries>() {

    override fun onBindViewHold(binding: MovieRowBinding, position: Int, context: Context) {
        val tvSeries = getItem(position)

        if (tvSeries != null) {
            binding.ivPoster.load(
                ImageApi.getImage(
                    imageSize = ImageSize.W185.path,
                    imageUrl = tvSeries.posterPath
                ),
                imageLoader = imageLoader
            )

            binding.tvMovieTvName.text = tvSeries.name

            val genre = Util.handleGenreOneText(genreList, tvSeries.genreIds)
            val releaseDate = Util.handleReleaseDate(tvSeries.firstAirDate)

            val voteCount = Util.handleVoteCount(tvSeries.voteCount)

            binding.tvReleaseDateGenre.text =
                context.getString(R.string.release_date_genre, releaseDate, genre)

            binding.voteAverage.text = context.getString(
                R.string.voteAverage,
                tvSeries.voteAverage.toString(),
                voteCount
            )
        }
    }

    override fun passMovieGenreList(genreList: List<Genre>) {
        this.genreList = genreList
    }
}