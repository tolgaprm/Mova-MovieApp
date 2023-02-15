package com.prmto.mova_movieapp.feature_explore.domain.model

import com.prmto.mova_movieapp.core.domain.models.TvSeries

data class TvSearch(
    val id: Int,
    val overview: String,
    val name: String,
    val originalName: String,
    val posterPath: String?,
    val firstAirDate: String?,
    val genreIds: List<Int>,
    val voteCount: Int,
    val voteAverage: Double,
    val genreByOneForTv: String = "",
    val voteCountByString: String = ""
)

fun TvSearch.toTvSeries(): TvSeries {
    return TvSeries(
        id = id,
        overview = overview,
        name = name,
        originalName = originalName,
        posterPath = posterPath,
        firstAirDate = firstAirDate,
        genreIds = genreIds,
        voteCount = voteCount,
        voteAverage = voteAverage,
        genreByOne = genreByOneForTv,
        voteCountByString = voteCountByString
    )
}