package com.prmto.mova_movieapp.presentation.home.recyler

import android.content.Context
import coil.ImageLoader
import coil.load
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.data.remote.ImageApi
import com.prmto.mova_movieapp.data.remote.ImageSize
import com.prmto.mova_movieapp.databinding.MovieRowBinding
import com.prmto.mova_movieapp.domain.models.Genre
import com.prmto.mova_movieapp.domain.models.Movie
import com.prmto.mova_movieapp.presentation.util.BaseMovieAndTvRecyclerAdapter
import com.prmto.mova_movieapp.presentation.util.Util
import javax.inject.Inject

class PopularMoviesAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) : BaseMovieAndTvRecyclerAdapter<Movie>() {

    override fun onBindViewHold(
        binding: MovieRowBinding,
        position: Int,
        context: Context
    ) {

        val movie = getItem(position)

        if (movie != null) {
            binding.ivPoster.load(
                ImageApi.getImage(
                    imageSize = ImageSize.W185.path,
                    imageUrl = movie.posterPath
                ),
                imageLoader = imageLoader
            )

            binding.tvMovieTvName.text = movie.title

            val genre = Util.handleGenreOneText(genreList, movie.genreIds)
            val releaseDate = Util.handleReleaseDate(movie.releaseDate)

            val voteCount = Util.handleVoteCount(movie.voteCount)

            binding.tvReleaseDateGenre.text =
                context.getString(R.string.release_date_genre, releaseDate, genre)

            binding.voteAverage.text = context.getString(
                R.string.voteAverage,
                movie.voteAverage.toString(),
                voteCount
            )

            binding.root.setOnClickListener {
                this.itemClickListener(movie)
            }
        }

    }


    override
    fun passMovieGenreList(genreList: List<Genre>) {
        this.genreList = genreList
    }
}