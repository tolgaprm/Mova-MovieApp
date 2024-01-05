package com.prmto.home_data.remote.api

import com.prmto.core_data.remote.dto.ApiResponse
import com.prmto.core_data.remote.dto.movie.MovieDto
import com.prmto.core_data.remote.dto.tv.TvSeriesDto
import com.prmto.core_data.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("region") region: String,
        @Query("language") language: String
    ): Response<ApiResponse<MovieDto>>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("region") region: String,
        @Query("language") language: String
    ): Response<ApiResponse<MovieDto>>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("region") region: String,
        @Query("language") language: String
    ): Response<ApiResponse<MovieDto>>


    @GET("tv/popular")
    suspend fun getPopularTvs(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("language") language: String,
    ): Response<ApiResponse<TvSeriesDto>>

    @GET("tv/top_rated")
    suspend fun getTopRatedTvs(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("language") language: String,
    ): Response<ApiResponse<TvSeriesDto>>
}