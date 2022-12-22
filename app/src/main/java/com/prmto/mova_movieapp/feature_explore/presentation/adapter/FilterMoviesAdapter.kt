package com.prmto.mova_movieapp.feature_explore.presentation.adapter

import android.content.Context
import android.view.View
import coil.ImageLoader
import coil.load
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.data.data_source.remote.ImageApi
import com.prmto.mova_movieapp.core.data.data_source.remote.ImageSize
import com.prmto.mova_movieapp.core.presentation.util.BaseMovieAndTvRecyclerAdapter
import com.prmto.mova_movieapp.databinding.MovieRowBinding
import com.prmto.mova_movieapp.feature_home.domain.models.Movie
import javax.inject.Inject


class FilterMoviesAdapter @Inject constructor(
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
