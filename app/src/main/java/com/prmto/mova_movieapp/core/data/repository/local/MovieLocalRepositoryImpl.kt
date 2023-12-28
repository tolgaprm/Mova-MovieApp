package com.prmto.mova_movieapp.core.data.repository.local

import com.prmto.mova_movieapp.core.data.local.MovaDatabase
import com.prmto.mova_movieapp.core.data.remote.mapper.movie.toFavoriteMovie
import com.prmto.mova_movieapp.core.data.remote.mapper.movie.toMovieWatchListItem
import com.prmto.mova_movieapp.core.domain.models.movie.Movie
import com.prmto.mova_movieapp.core.domain.repository.local.MovieLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieLocalRepositoryImpl @Inject constructor(
    private val movaDatabase: MovaDatabase
) : MovieLocalRepository {

    private val movieDao = movaDatabase.movieDao

    override suspend fun insertMovieToFavoriteList(movie: Movie) {
        movieDao.insertMovieToFavoriteList(
            favoriteMovie = movie.toFavoriteMovie()
        )
    }

    override suspend fun insertMovieToWatchList(movie: Movie) {
        movieDao.insertMovieToWatchListItem(
            movieWatchListItem = movie.toMovieWatchListItem()
        )
    }

    override suspend fun deleteMovieFromFavoriteList(movie: Movie) {
        movieDao.deleteMovieFromFavoriteList(favoriteMovie = movie.toFavoriteMovie())
    }

    override suspend fun deleteMovieFromWatchListItem(movie: Movie) {
        movieDao.deleteMovieFromWatchListItem(movieWatchListItem = movie.toMovieWatchListItem())
    }

    override fun getFavoriteMovieIds(): Flow<List<Int>> {
        return movieDao.getFavoriteMovieIds()
    }

    override fun getMovieWatchListItemIds(): Flow<List<Int>> {
        return movieDao.getMovieWatchListItemIds()
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return movieDao.getFavoriteMovies().map {
            it.map { it.movie }
        }
    }

    override fun getMoviesInWatchList(): Flow<List<Movie>> {
        return movieDao.getMovieWatchList().map {
            it.map { it.movie }
        }
    }
}