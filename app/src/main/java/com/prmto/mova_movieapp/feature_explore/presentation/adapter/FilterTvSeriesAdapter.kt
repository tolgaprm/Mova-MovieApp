package com.prmto.mova_movieapp.feature_explore.presentation.adapter

import android.content.Context
import android.view.View
import coil.load
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.base.BaseMovieAndTvRecyclerAdapter
import com.prmto.mova_movieapp.core.presentation.util.ImageSize
import com.prmto.mova_movieapp.core.presentation.util.ImageUtil
import com.prmto.mova_movieapp.databinding.MovieRowBinding

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
            binding.txtCategory.text = context.getText(R.string.tv)
        }
    }

}