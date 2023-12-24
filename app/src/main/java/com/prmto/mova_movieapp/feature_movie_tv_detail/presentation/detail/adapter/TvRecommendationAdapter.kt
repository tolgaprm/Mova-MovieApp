package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.adapter

import android.content.Context
import coil.load
import com.prmto.mova_movieapp.core.data.remote.api.ImageApi
import com.prmto.mova_movieapp.core.data.remote.api.ImageSize
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.presentation.util.BaseMovieAndTvRecyclerAdapter
import com.prmto.mova_movieapp.databinding.MovieRowBinding

class TvRecommendationAdapter : BaseMovieAndTvRecyclerAdapter<TvSeries>() {

    override fun onBindViewHold(binding: MovieRowBinding, position: Int, context: Context) {
        val tvSeries = getItem(position)

        if (tvSeries != null) {
            binding.ivPoster.load(
                ImageApi.getImage(
                    imageSize = ImageSize.W185.path,
                    imageUrl = tvSeries.posterPath
                )
            )

            binding.root.setOnClickListener {
                this.itemClickListener(tvSeries)
            }
        }
    }
}