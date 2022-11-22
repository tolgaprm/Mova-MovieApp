package com.prmto.mova_movieapp.presentation.home.recyler

import android.content.Context
import coil.ImageLoader
import coil.load
import com.prmto.mova_movieapp.data.models.Genre
import com.prmto.mova_movieapp.data.remote.ImageApi
import com.prmto.mova_movieapp.data.remote.ImageSize
import com.prmto.mova_movieapp.databinding.MovieRowBinding
import com.prmto.mova_movieapp.domain.models.Movie
import com.prmto.mova_movieapp.presentation.util.BaseMovieAndTvRecyclerAdapter
import javax.inject.Inject

class TopRatedMoviesAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) : BaseMovieAndTvRecyclerAdapter<Movie>() {

    override fun onBindViewHold(binding: MovieRowBinding, position: Int, context: Context) {
        val movie = getItem(position)

        if (movie != null) {
            binding.ivPoster.load(
                ImageApi.getImage(
                    imageSize = ImageSize.W185.path,
                    imageUrl = movie.posterPath
                ),
                imageLoader = imageLoader
            )

            binding.root.setOnClickListener {
                this.itemClickListener(movie)
            }
        }
    }

    override fun passMovieGenreList(genreList: List<Genre>) {
        this.genreList = genreList
    }
}