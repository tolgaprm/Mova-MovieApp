package com.prmto.explore_ui.adapter.viewHolder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_ui.databinding.NowPlayingRowBinding
import com.prmto.core_ui.util.ImageSize
import com.prmto.core_ui.util.ImageUtil
import com.prmto.core_ui.R as CoreUiR

class SearchTvViewHolder(
    val binding: NowPlayingRowBinding,
    val context: Context
) : ViewHolder(binding.root) {

    fun bindSearchTv(
        tvSeries: TvSeries,
        onSearchTvItemClick: (TvSeries) -> Unit = {}
    ) {
        binding.backdropImage.load(
            ImageUtil.getImage(
                imageUrl = tvSeries.posterPath,
                imageSize = ImageSize.W500.path
            )
        )

        binding.voteAverage.text = context.getString(
            CoreUiR.string.voteAverage,
            tvSeries.voteAverage.toString(),
            tvSeries.formattedVoteCount
        )

        binding.genresText.text = tvSeries.genreByOne

        binding.movieTitle.textSize = 16f
        binding.movieTitle.text = tvSeries.name
        binding.txtCategory.visibility = View.VISIBLE
        binding.txtCategory.text = context.getString(CoreUiR.string.tv)

        binding.root.setOnClickListener {
            onSearchTvItemClick(tvSeries)
        }
    }

    companion object {
        fun from(parent: ViewGroup): SearchTvViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = NowPlayingRowBinding.inflate(layoutInflater, parent, false)
            return SearchTvViewHolder(
                binding = binding,
                context = parent.context
            )
        }
    }
}