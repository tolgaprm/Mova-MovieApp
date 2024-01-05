package com.prmto.core_data.remote.mapper.movie

import com.prmto.core_data.remote.dto.movie.MovieDto
import com.prmto.core_data.util.HandleUtils
import com.prmto.core_data.util.orZero
import com.prmto.core_domain.models.movie.Movie
import com.prmto.database.entity.movie.FavoriteMovie
import com.prmto.database.entity.movie.MovieWatchListItem

fun Movie.toFavoriteMovie(): FavoriteMovie {
    return FavoriteMovie(
        movieId = this.id,
        movie = this
    )
}

fun Movie.toMovieWatchListItem(): MovieWatchListItem {
    return MovieWatchListItem(
        movieId = this.id,
        movie = this
    )
}

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id.orZero(),
        overview = overview.orEmpty(),
        title = title.orEmpty(),
        posterPath = posterPath,
        releaseDate = HandleUtils.convertToYearFromDate(releaseDate),
        genreIds = genreIds.orEmpty(),
        voteAverage = voteAverage.orZero(),
        formattedVoteCount = HandleUtils.formatVoteCount(voteCount),
        fullReleaseDate = releaseDate
    )
}