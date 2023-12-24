package com.prmto.mova_movieapp.feature_explore.domain.model

import com.prmto.mova_movieapp.core.domain.models.Movie

data class MovieSearch(
    val id: Int,
    val overview: String,
    val title: String,
    val originalTitle: String,
    val posterPath: String?,
    var releaseDate: String,
    val genreIds: List<Int>,
    val voteCount: Int,
    val voteAverage: Double,
    val genreByOneForMovie: String = "",
    val voteCountByString: String = ""
)

fun MovieSearch.toMovie(): Movie {
    return Movie(
        id = id,
        overview = overview,
        posterPath = posterPath,
        genreIds = genreIds,
        voteCount = voteCount,
        voteAverage = voteAverage,
        genreByOne = genreByOneForMovie,
        voteCountByString = voteCountByString,
        title = title,
        originalTitle = originalTitle,
        releaseDate = releaseDate,
        genresBySeparatedByComma = genreByOneForMovie
    )
}