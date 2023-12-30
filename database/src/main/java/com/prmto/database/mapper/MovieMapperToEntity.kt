package com.prmto.database.mapper

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