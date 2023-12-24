package com.prmto.mova_movieapp.core.data.remote.mapper.tv

import com.prmto.mova_movieapp.core.data.local.entity.tv.FavoriteTvSeries
import com.prmto.mova_movieapp.core.data.local.entity.tv.TvSeriesWatchListItem
import com.prmto.mova_movieapp.core.data.remote.dto.tv.TvSeriesDto
import com.prmto.mova_movieapp.core.data.util.orZero
import com.prmto.mova_movieapp.core.domain.models.TvSeries

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

fun FavoriteTvSeries.toTvSeries(): TvSeries {
    return TvSeries(
        id = tvSeriesId,
        overview = this.tvSeries.overview,
        name = this.tvSeries.name,
        originalName = this.tvSeries.originalName,
        posterPath = this.tvSeries.posterPath,
        firstAirDate = this.tvSeries.firstAirDate,
        genreIds = this.tvSeries.genreIds,
        voteCount = this.tvSeries.voteCount,
        voteAverage = this.tvSeries.voteAverage,
        genreByOne = this.tvSeries.genreByOne,
        voteCountByString = this.tvSeries.voteCountByString
    )
}

fun TvSeriesDto.toTvSeries(): TvSeries {
    return TvSeries(
        id = id.orZero(),
        overview = overview.orEmpty(),
        name = name.orEmpty(),
        originalName = originalName.orEmpty(),
        posterPath = posterPath,
        firstAirDate = firstAirDate,
        genreIds = genreIds.orEmpty(),
        voteCount = voteCount.orZero(),
        voteAverage = voteAverage.orZero()
    )
}