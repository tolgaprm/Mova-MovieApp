package com.prmto.mova_movieapp.core.data.remote.mapper.movie

import com.prmto.mova_movieapp.core.data.local.entity.movie.FavoriteMovie
import com.prmto.mova_movieapp.core.data.local.entity.movie.MovieWatchListItem
import com.prmto.mova_movieapp.core.data.remote.dto.movie.MovieDto
import com.prmto.mova_movieapp.core.data.util.HandleUtils
import com.prmto.mova_movieapp.core.data.util.orZero
import com.prmto.mova_movieapp.core.domain.models.movie.Movie

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