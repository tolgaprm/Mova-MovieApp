package com.prmto.mova_movieapp.core.data.mapper

import com.prmto.mova_movieapp.core.data.data_source.local.models.movie.FavoriteMovie
import com.prmto.mova_movieapp.core.data.data_source.local.models.movie.MovieWatchListItem
import com.prmto.mova_movieapp.core.data.dto.MovieDto
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
        id = id,
        overview = overview,
        title = title,
        originalTitle = originalTitle,
        posterPath = posterPath,
        releaseDate = releaseDate,
        genreIds = genreIds,
        voteCount = voteCount,
        voteAverage = voteAverage
    )
}