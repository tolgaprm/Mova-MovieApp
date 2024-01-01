package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.adapter

import android.content.Context
import coil.load
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_ui.databinding.MovieRowBinding

class TvRecommendationAdapter : com.prmto.core_ui.base.BaseMovieAndTvRecyclerAdapter<TvSeries>() {

    override fun onBindViewHold(binding: MovieRowBinding, position: Int, context: Context) {
        val tvSeries = getItem(position)

        if (tvSeries != null) {
            binding.ivPoster.load(
                com.prmto.core_ui.util.ImageUtil.getImage(
                    imageSize = com.prmto.core_ui.util.ImageSize.W185.path,
                    imageUrl = tvSeries.posterPath
                )
            )

            binding.root.setOnClickListener {
                this.itemClickListener(tvSeries)
            }
        }
    }
}