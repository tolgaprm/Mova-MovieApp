package com.prmto.home_ui.adapter

import android.content.Context
import coil.load
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_ui.base.BaseMovieAndTvRecyclerAdapter
import com.prmto.core_ui.databinding.MovieRowBinding
import com.prmto.core_ui.util.ImageSize
import com.prmto.core_ui.util.ImageUtil

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