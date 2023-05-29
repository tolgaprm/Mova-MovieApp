package com.prmto.mova_movieapp.feature_upcoming.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.prmto.mova_movieapp.core.data.data_source.remote.ImageApi
import com.prmto.mova_movieapp.core.data.data_source.remote.ImageSize
import com.prmto.mova_movieapp.databinding.ComingSoonItemBinding
import com.prmto.mova_movieapp.feature_home.presentation.home.adapter.DiffUtilCallBack
import com.prmto.mova_movieapp.feature_person_detail.domain.util.DateFormatUtils

abstract class UpComingBaseAdapter<T : Any> :
    PagingDataAdapter<T, UpComingBaseAdapter.UpComingViewHolder>(DiffUtilCallBack<T>()) {

    class UpComingViewHolder(
        val binding: ComingSoonItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            posterPath: String?,
            movieTvName: String,
            overview: String,
            releaseDate: String,
            genresBySeparatedByComma: String
        ) {
            binding.imvMoviePoster.load(
                ImageApi.getImage(
                    imageSize = ImageSize.W300.path,
                    imageUrl = posterPath
                )
            )
            binding.imvMovieBackdrop.load(
                ImageApi.getImage(
                    imageSize = ImageSize.W300.path,
                    imageUrl = posterPath
                )
            )
            binding.txtMovieName.text = movieTvName

            binding.txtOverview.text = overview

            binding.txtReleaseDate.text = DateFormatUtils.convertDateFormat(releaseDate)

            binding.txtGenre.text = genresBySeparatedByComma
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpComingViewHolder {
        val binding = ComingSoonItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UpComingViewHolder(
            binding = binding
        )
    }
}
