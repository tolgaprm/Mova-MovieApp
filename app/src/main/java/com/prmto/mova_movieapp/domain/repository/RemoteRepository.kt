package com.prmto.mova_movieapp.domain.repository

import androidx.paging.PagingData
import com.prmto.mova_movieapp.data.models.detail.movie.MovieDetailDto
import com.prmto.mova_movieapp.data.models.detail.tv.TvDetailDto
import com.prmto.mova_movieapp.domain.models.Movie
import com.prmto.mova_movieapp.domain.models.TvSeries
import com.prmto.mova_movieapp.presentation.filter_bottom_sheet.state.FilterBottomState
import com.prmto.mova_movieapp.util.Constants.DEFAULT_REGION
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    suspend fun getMovieGenreList(
        language: String
    ): com.prmto.mova_movieapp.data.models.GenreList

    suspend fun getTvGenreList(
        language: String
    ): com.prmto.mova_movieapp.data.models.GenreList

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

    fun getPopularTvs(
        language: String
    ): Flow<PagingData<TvSeries>>

    fun getTopRatedTvs(
        language: String
    ): Flow<PagingData<TvSeries>>

    fun discoverMovie(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<Movie>>


    fun discoverTv(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<TvSeries>>

    suspend fun getMovieDetail(
        language: String,
        movieId: Int
    ): MovieDetailDto

    suspend fun getTvDetail(
        language: String,
        tvId: Int
    ): TvDetailDto
}