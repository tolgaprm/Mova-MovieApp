package com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote

import com.prmto.mova_movieapp.core.data.dto.ApiResponse
import com.prmto.mova_movieapp.core.util.Constants
import com.prmto.mova_movieapp.feature_home.data.dto.MovieDto
import com.prmto.mova_movieapp.feature_home.data.dto.TvSeriesDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.movie.MovieDetailDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.tv.TvDetailDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailApi {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
        @Query("append_to_response") appendToResponse: String = Constants.QUERY_APPEND_TO_RESPONSE
    ): MovieDetailDto

    @GET("tv/{tv_id}")
    suspend fun getTvDetail(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String,
        @Query("append_to_response") appendToResponse: String = Constants.QUERY_APPEND_TO_RESPONSE
    ): TvDetailDto

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendationsForMovie(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
        @Query("page") page: Int
    ): ApiResponse<MovieDto>

    @GET("tv/{tv_id}/recommendations")
    suspend fun getRecommendationsForTv(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String,
        @Query("page") page: Int
    ): ApiResponse<TvSeriesDto>

}