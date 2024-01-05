package com.prmto.core_domain.repository.local

import com.prmto.core_domain.models.movie.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalRepository {
    suspend fun insertMovieToFavoriteList(movie: Movie)

    suspend fun insertMovieToWatchList(movie: Movie)

    suspend fun deleteMovieFromFavoriteList(movie: Movie)

    suspend fun deleteMovieFromWatchListItem(movie: Movie)

    fun getFavoriteMovieIds(): Flow<List<Int>>

    fun getMovieWatchListItemIds(): Flow<List<Int>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    fun getMoviesInWatchList(): Flow<List<Movie>>

}