package com.prmto.mova_movieapp.feature_movie_tv_detail.data.api

import com.prmto.mova_movieapp.core.data.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.dto.MovieDto
import com.prmto.mova_movieapp.core.data.dto.TvSeriesDto
import com.prmto.mova_movieapp.core.util.Constants
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.movie.MovieDetailDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.tv.TvDetailDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.video.VideosDto
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