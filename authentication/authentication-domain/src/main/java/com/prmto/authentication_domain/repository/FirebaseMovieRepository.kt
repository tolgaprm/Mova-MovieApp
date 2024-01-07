package com.prmto.authentication_domain.repository

import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.util.Resource

interface FirebaseMovieRepository {

    suspend fun getFavoriteMovie(
        userUid: String
    ): Resource<List<Movie>>

    suspend fun getMovieInWatchList(
        userUid: String
    ): Resource<List<Movie>>
}