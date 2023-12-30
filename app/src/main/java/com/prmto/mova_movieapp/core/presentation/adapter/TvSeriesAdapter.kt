package com.prmto.mova_movieapp.core.presentation.adapter

import com.prmto.core_domain.models.tv.TvSeries

class TvSeriesAdapter : BaseListAdapter<TvSeries>() {

    override fun onBindViewHolder(holder: BaseListViewHolder, position: Int) {
        val tvSeries = getItem(position)
        holder.bind(
            context = holder.itemView.context,
            posterPath = tvSeries.posterPath,
            movieTvName = tvSeries.name,
            voteAverage = tvSeries.voteAverage.toString(),
            voteCountByString = tvSeries.formattedVoteCount,
            releaseDate = tvSeries.firstAirDate,
            genreByOne = tvSeries.genreByOne
        )

        holder.itemView.setOnClickListener {
            super.itemClickListener(tvSeries)
        }
    }
}