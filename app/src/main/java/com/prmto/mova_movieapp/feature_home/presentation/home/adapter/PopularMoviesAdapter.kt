package com.prmto.mova_movieapp.feature_home.presentation.home.adapter

import android.content.Context
import coil.load
import com.prmto.mova_movieapp.core.domain.models.movie.Movie
import com.prmto.mova_movieapp.core.presentation.base.BaseMovieAndTvRecyclerAdapter
import com.prmto.mova_movieapp.core.presentation.util.ImageSize
import com.prmto.mova_movieapp.core.presentation.util.ImageUtil
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
                ImageUtil.getImage(
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