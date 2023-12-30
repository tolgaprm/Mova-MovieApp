package com.prmto.mova_movieapp.feature_home.data.remote.dataSources.movie

import com.prmto.core_data.remote.dto.ApiResponse
import com.prmto.core_data.remote.dto.movie.MovieDto

interface HomeMovieRemoteDataSource {

    suspend fun getNowPlayingMovies(
        language: String,
        region: String,
        page: Int
    ): ApiResponse<MovieDto>

    suspend fun getPopularMovies(
        language: String,
        region: String,
        page: Int
    ): ApiResponse<MovieDto>

    suspend fun getTopRatedMovies(
        language: String,
        region: String,
        page: Int
    ): ApiResponse<MovieDto>
}