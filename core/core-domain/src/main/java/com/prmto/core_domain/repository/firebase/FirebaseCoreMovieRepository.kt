package com.prmto.core_domain.repository.firebase

import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.util.SimpleResource

interface FirebaseCoreMovieRepository {
    suspend fun addMovieToFavoriteList(
        userUid: String,
        movieInFavoriteList: List<Movie>
    ): SimpleResource

    suspend fun addMovieToWatchList(
        userUid: String,
        moviesInWatchList: List<Movie>
    ): SimpleResource
}