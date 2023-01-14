package com.prmto.mova_movieapp.feature_person_detail.domain.model

import com.prmto.mova_movieapp.feature_home.domain.models.Movie
import com.prmto.mova_movieapp.feature_home.domain.models.TvSeries

data class CastForPerson(
        val id: Int,
        val name: String?,
        val originalName: String?,
        val originalTitle: String?,
        val character: String,
        val firstAirDate: String?,
        val mediaType: String,
        val overview: String,
        val posterPath: String?,
        val releaseDate: String?,
        val voteAverage: Double,
        val voteCount: Int,
        val title: String?,
        val popularity: Double
)


fun CastForPerson.toMovie(): Movie {
    return Movie(
            id = id,
            overview = overview,
            title = title ?: "",
            originalTitle = originalTitle ?: "",
            posterPath = posterPath,
            releaseDate = releaseDate,
            voteCount = voteCount,
            genreIds = emptyList(),
            voteAverage = voteAverage
    )
}

fun CastForPerson.toTvSeries(): TvSeries {
    return TvSeries(
            id = id,
            overview = overview,
            posterPath = posterPath,
            voteCount = voteCount,
            genreIds = emptyList(),
            voteAverage = voteAverage,
            name = name ?: "",
            originalName = originalName ?: "",
            firstAirDate = firstAirDate
    )
}