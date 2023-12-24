package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.tv.model

import com.prmto.mova_movieapp.core.data.dto.Genre
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.tv.Season
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.credit.Credit
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.watchProvider.WatchProviderItem

data class TvDetail(
    val id: Int,
    val genres: List<Genre>,
    val firstAirDate: String,
    val createdBy: List<CreatedBy>?,
    val lastAirDate: String,
    val numberOfSeasons: Int,
    val originalName: String,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val seasons: List<Season>,
    val status: String,
    val voteAverage: Double,
    val voteCount: Int,
    var ratingValue: Float = 0f,
    var releaseDate: String = "",
    val credit: Credit?,
    val watchProviders: WatchProviderItem?
)

fun TvDetail.toTvSeries(): TvSeries {
    return TvSeries(
        id = id,
        overview = overview,
        name = name,
        originalName = originalName,
        posterPath = posterPath,
        firstAirDate = firstAirDate,
        genreIds = genres.map { it.id },
        voteCount = voteCount,
        voteAverage = voteAverage,
        genreByOne = genres.first().name
    )
}
