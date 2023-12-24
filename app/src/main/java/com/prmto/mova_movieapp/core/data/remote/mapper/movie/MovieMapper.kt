package com.prmto.mova_movieapp.core.data.remote.mapper.movie

import com.prmto.mova_movieapp.core.data.local.entity.movie.FavoriteMovie
import com.prmto.mova_movieapp.core.data.local.entity.movie.MovieWatchListItem
import com.prmto.mova_movieapp.core.data.remote.dto.movie.MovieDto
import com.prmto.mova_movieapp.core.data.util.orZero
import com.prmto.mova_movieapp.core.domain.models.Movie

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
        originalTitle = originalTitle.orEmpty(),
        posterPath = posterPath,
        releaseDate = releaseDate,
        genreIds = genreIds.orEmpty(),
        voteCount = voteCount.orZero(),
        voteAverage = voteAverage.orZero()
    )
}