package com.prmto.mova_movieapp.feature_explore.presentation.adapter

import android.content.Context
import android.view.View
import coil.load
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_ui.base.BaseMovieAndTvRecyclerAdapter
import com.prmto.core_ui.databinding.MovieRowBinding
import com.prmto.mova_movieapp.R

class FilterMoviesAdapter : BaseMovieAndTvRecyclerAdapter<Movie>() {

    override fun onBindViewHold(
        binding: MovieRowBinding,
        position: Int,
        context: Context
    ) {

        val movie = getItem(position)

        if (movie != null) {
            binding.ivPoster.load(
                com.prmto.core_ui.util.ImageUtil.getImage(
                    imageSize = com.prmto.core_ui.util.ImageSize.W185.path,
                    imageUrl = movie.posterPath
                )
            )

            binding.root.setOnClickListener {
                this.itemClickListener(movie)
            }

            binding.root.setOnClickListener {
                this.itemClickListener(movie)
            }
            binding.txtCategory.visibility = View.VISIBLE
            binding.txtCategory.text = context.getText(R.string.movie)
        }
    }
}
