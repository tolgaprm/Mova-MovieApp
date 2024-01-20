package com.prmto.core_domain.fake.repository.local

import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.repository.local.MovieLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMovieLocalRepository : MovieLocalRepository {
    private val favoriteMovie = mutableListOf<Movie>()
    private val watchListMovie = mutableListOf<Movie>()
    override suspend fun insertMovieToFavoriteList(movie: Movie) {
        favoriteMovie.add(movie)
    }

    override suspend fun insertMovieToWatchList(movie: Movie) {
        watchListMovie.add(movie)
    }

    override suspend fun deleteMovieFromFavoriteList(movie: Movie) {
        if (movie in favoriteMovie) {
            favoriteMovie.remove(movie)
        }
    }

    override suspend fun deleteMovieFromWatchListItem(movie: Movie) {
        if (movie in watchListMovie) {
            watchListMovie.remove(movie)
        }
    }

    override fun getFavoriteMovieIds(): Flow<List<Int>> {
        val favoriteMovieIds = favoriteMovie.map { it.id }
        return flow { emit(favoriteMovieIds) }
    }

    override fun getMovieWatchListItemIds(): Flow<List<Int>> {
        val watchListMovieIds = watchListMovie.map { it.id }
        return flow { emit(watchListMovieIds) }
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return flow { emit(favoriteMovie) }
    }

    override fun getMoviesInWatchList(): Flow<List<Movie>> {
        return flow { emit(watchListMovie) }
    }
}