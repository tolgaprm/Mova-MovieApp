package com.prmto.core_domain.fake.models.movie

import com.prmto.core_domain.models.movie.Movie

fun movie(
    id: Int,
    title: String,
): Movie {
    return Movie(
        id = id,
        overview = "",
        title = title,
        isFavorite = false,
        isWatchList = false,
        posterPath = "",
        releaseDate = "",
        fullReleaseDate = "",
        genreIds = listOf(),
        genresBySeparatedByComma = "",
        voteAverage = 0.0,
        genreByOne = "",
        formattedVoteCount = "",
    )
}