package com.prmto.mova_movieapp.feature_explore.presentation.adapter

import android.content.Context
import android.view.View
import coil.load
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_ui.base.BaseMovieAndTvRecyclerAdapter
import com.prmto.core_ui.databinding.MovieRowBinding
import com.prmto.core_ui.util.ImageSize
import com.prmto.core_ui.util.ImageUtil
import com.prmto.core_ui.R as CoreUiR

class FilterTvSeriesAdapter : BaseMovieAndTvRecyclerAdapter<TvSeries>() {

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

            binding.root.setOnClickListener {
                this.itemClickListener(tvSeries)
            }
            binding.txtCategory.visibility = View.VISIBLE
            binding.txtCategory.text = context.getText(CoreUiR.string.tv)
        }
    }

}