package com.prmto.mova_movieapp.feature_explore.presentation.adapter

import android.content.Context
import android.view.View
import coil.load
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.data.data_source.remote.ImageApi
import com.prmto.mova_movieapp.core.data.data_source.remote.ImageSize
import com.prmto.mova_movieapp.core.presentation.util.BaseMovieAndTvRecyclerAdapter
import com.prmto.mova_movieapp.databinding.MovieRowBinding
import com.prmto.mova_movieapp.feature_home.domain.models.TvSeries

class FilterTvSeriesAdapter : BaseMovieAndTvRecyclerAdapter<TvSeries>() {

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

            binding.root.setOnClickListener {
                this.itemClickListener(tvSeries)
            }
            binding.txtCategory.visibility = View.VISIBLE
            binding.txtCategory.text = context.getText(R.string.tv)
        }
    }

}