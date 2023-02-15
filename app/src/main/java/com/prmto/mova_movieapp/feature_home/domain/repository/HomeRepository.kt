package com.prmto.mova_movieapp.feature_home.domain.repository

import androidx.paging.PagingData
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.util.Constants
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getNowPlayingMovies(
        language: String,
        region: String = Constants.DEFAULT_REGION
    ): Flow<PagingData<Movie>>

    fun getPopularMovies(
        language: String,
        region: String
    ): Flow<PagingData<Movie>>

    fun getTopRatedMovies(
        language: String,
        region: String
    ): Flow<PagingData<Movie>>

    fun getPopularTvs(
        language: String
    ): Flow<PagingData<TvSeries>>

    fun getTopRatedTvs(
        language: String
    ): Flow<PagingData<TvSeries>>

}