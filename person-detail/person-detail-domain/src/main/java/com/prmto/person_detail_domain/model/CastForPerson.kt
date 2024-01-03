package com.prmto.person_detail_domain.model

import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.models.tv.TvSeries

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
            posterPath = posterPath,
            releaseDate = releaseDate,
            genreIds = emptyList(),
            voteAverage = voteAverage
    )
}

fun CastForPerson.toTvSeries(): TvSeries {
    return TvSeries(
            id = id,
            overview = overview,
            posterPath = posterPath,
            genreIds = emptyList(),
            voteAverage = voteAverage,
            name = name ?: "",
            firstAirDate = firstAirDate
    )
}