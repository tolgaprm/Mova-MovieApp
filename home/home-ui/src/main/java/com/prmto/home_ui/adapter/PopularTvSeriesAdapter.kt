package com.prmto.home_ui.adapter

import android.content.Context
import coil.load
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_ui.base.BaseMovieAndTvRecyclerAdapter
import com.prmto.core_ui.databinding.MovieRowBinding
import com.prmto.core_ui.util.ImageSize
import com.prmto.core_ui.util.ImageUtil

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