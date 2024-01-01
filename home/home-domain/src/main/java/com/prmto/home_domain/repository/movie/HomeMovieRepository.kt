package com.prmto.home_domain.repository.movie

import androidx.paging.PagingData
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.util.Constants.DEFAULT_REGION
import kotlinx.coroutines.flow.Flow

interface HomeMovieRepository {
    fun getNowPlayingMovies(
        language: String,
        region: String = DEFAULT_REGION
    ): Flow<PagingData<Movie>>

    fun getPopularMovies(
        language: String,
        region: String
    ): Flow<PagingData<Movie>>

    fun getTopRatedMovies(
        language: String,
        region: String
    ): Flow<PagingData<Movie>>
}