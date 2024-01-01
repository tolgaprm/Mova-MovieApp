package com.prmto.data.remote.api

import com.prmto.core_data.remote.dto.ApiResponse
import com.prmto.core_data.remote.dto.movie.MovieDto
import com.prmto.core_data.remote.dto.tv.TvSeriesDto
import com.prmto.core_data.util.Constants
import com.prmto.data.remote.dto.detail.movie.MovieDetailDto
import com.prmto.data.remote.dto.detail.tv.TvDetailDto
import com.prmto.data.remote.dto.detail.video.VideosDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailApi {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
        @Query("append_to_response") appendToResponse: String = Constants.QUERY_APPEND_TO_RESPONSE
    ): Response<MovieDetailDto>

    @GET("tv/{tv_id}")
    suspend fun getTvDetail(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String,
        @Query("append_to_response") appendToResponse: String = Constants.QUERY_APPEND_TO_RESPONSE
    ): Response<TvDetailDto>

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendationsForMovie(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): Response<ApiResponse<MovieDto>>

    @GET("tv/{tv_id}/recommendations")
    suspend fun getRecommendationsForTv(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String,
        @Query("page") page: Int = 1
    ): Response<ApiResponse<TvSeriesDto>>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String
    ): Response<VideosDto>

    @GET("tv/{tv_id}/videos")
    suspend fun getTvVideos(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String
    ): Response<VideosDto>
}