package com.prmto.mova_movieapp.domain.repository

import androidx.paging.PagingData
import com.prmto.mova_movieapp.domain.models.GenreList
import com.prmto.mova_movieapp.domain.models.Movie
import com.prmto.mova_movieapp.domain.models.TvSeries
import com.prmto.mova_movieapp.util.Constants.DEFAULT_REGION
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    suspend fun getMovieGenreList(
        language: String
    ): GenreList

    suspend fun getTvGenreList(
        language: String
    ): GenreList

    fun getNowPlayingMovies(
        language: String,
        region: String = DEFAULT_REGION
    ): Flow<PagingData<Movie>>

    fun getPopularMovies(
        language: String
    ): Flow<PagingData<Movie>>

    fun getTopRatedMovies(
        language: String
    ): Flow<PagingData<Movie>>

    fun getPopularTvs(
        language: String
    ): Flow<PagingData<TvSeries>>

    fun getTopRatedTvs(
        language: String
    ): Flow<PagingData<TvSeries>>
}