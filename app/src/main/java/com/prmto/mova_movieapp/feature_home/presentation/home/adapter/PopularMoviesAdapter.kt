package com.prmto.mova_movieapp.feature_home.presentation.home.adapter

import android.content.Context
import coil.load
import com.prmto.mova_movieapp.core.data.data_source.remote.ImageApi
import com.prmto.mova_movieapp.core.data.data_source.remote.ImageSize
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.presentation.util.BaseMovieAndTvRecyclerAdapter
import com.prmto.mova_movieapp.databinding.MovieRowBinding

class PopularMoviesAdapter : BaseMovieAndTvRecyclerAdapter<Movie>() {

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
                )
            )

            binding.root.setOnClickListener {
                this.itemClickListener(movie)
            }
        }

    }
}