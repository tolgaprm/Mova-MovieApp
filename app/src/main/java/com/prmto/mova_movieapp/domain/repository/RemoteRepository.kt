package com.prmto.mova_movieapp.domain.repository

import androidx.paging.PagingData
import com.prmto.mova_movieapp.domain.models.GenreList
import com.prmto.mova_movieapp.domain.models.Movie
import com.prmto.mova_movieapp.domain.models.TvSeries
import com.prmto.mova_movieapp.util.Constants.DEFAULT_LANGUAGE
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
        language: String = DEFAULT_LANGUAGE,
        region: String = DEFAULT_REGION
    ): Flow<PagingData<Movie>>

    fun getPopularMovies(
        language: String = DEFAULT_LANGUAGE
    ): Flow<PagingData<Movie>>

    fun getTopRatedMovies(
        language: String = DEFAULT_LANGUAGE
    ): Flow<PagingData<Movie>>

    fun getPopularTvs(
        language: String = DEFAULT_LANGUAGE
    ): Flow<PagingData<TvSeries>>
}