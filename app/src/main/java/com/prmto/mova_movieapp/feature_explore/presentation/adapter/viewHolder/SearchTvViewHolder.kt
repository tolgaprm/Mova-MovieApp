package com.prmto.mova_movieapp.feature_explore.presentation.adapter.viewHolder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.ImageLoader
import coil.load
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.data.data_source.remote.ImageApi
import com.prmto.mova_movieapp.core.data.data_source.remote.ImageSize
import com.prmto.mova_movieapp.databinding.NowPlayingRowBinding
import com.prmto.mova_movieapp.feature_explore.domain.model.TvSearch

class SearchTvViewHolder(
    val binding: NowPlayingRowBinding,
    val imageLoader: ImageLoader,
    val context: Context
) : ViewHolder(binding.root) {

    fun bindSearchTv(searchTv: TvSearch) {
        binding.backdropImage.load(
            ImageApi.getImage(
                imageUrl = searchTv.posterPath,
                imageSize = ImageSize.W500.path
            ),
            imageLoader = imageLoader
        )

        binding.voteAverage.text = context.getString(
            R.string.voteAverage,
            searchTv.voteAverage.toString(),
            searchTv.voteCountByString
        )

        binding.genresText.text = searchTv.genreByOneForTv

        binding.movieTitle.textSize = 16f
        binding.movieTitle.text = searchTv.name
        binding.txtCategory.visibility = View.VISIBLE
        binding.txtCategory.text = context.getString(R.string.tv)
    }

    companion object {
        fun from(parent: ViewGroup, imageLoader: ImageLoader): SearchTvViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = NowPlayingRowBinding.inflate(layoutInflater, parent, false)
            return SearchTvViewHolder(
                binding = binding,
                imageLoader = imageLoader,
                context = parent.context
            )
        }
    }
}