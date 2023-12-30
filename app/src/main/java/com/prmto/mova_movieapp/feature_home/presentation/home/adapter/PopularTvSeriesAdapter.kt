package com.prmto.mova_movieapp.feature_home.presentation.home.adapter

import android.content.Context
import coil.load
import com.prmto.mova_movieapp.core.domain.models.tv.TvSeries
import com.prmto.mova_movieapp.core.presentation.base.BaseMovieAndTvRecyclerAdapter
import com.prmto.mova_movieapp.core.presentation.util.ImageSize
import com.prmto.mova_movieapp.core.presentation.util.ImageUtil
import com.prmto.mova_movieapp.databinding.MovieRowBinding

class PopularTvSeriesAdapter : BaseMovieAndTvRecyclerAdapter<TvSeries>() {

    override fun onBindViewHold(binding: MovieRowBinding, position: Int, context: Context) {
        val tvSeries = getItem(position)

        if (tvSeries != null) {
            binding.ivPoster.load(
                ImageUtil.getImage(
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