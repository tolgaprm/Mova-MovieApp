package com.prmto.database.mapper

import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.database.entity.tv.FavoriteTvSeries
import com.prmto.database.entity.tv.TvSeriesWatchListItem

fun TvSeries.toFavoriteTvSeries(): FavoriteTvSeries {
    return FavoriteTvSeries(
        tvSeriesId = this.id,
        tvSeries = this
    )
}

fun TvSeries.toTvSeriesWatchListItem(): TvSeriesWatchListItem {
    return TvSeriesWatchListItem(
        tvSeriesId = this.id,
        tvSeries = this
    )
}