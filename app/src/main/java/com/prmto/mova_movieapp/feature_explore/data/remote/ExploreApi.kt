package com.prmto.mova_movieapp.feature_explore.data.remote

import com.prmto.mova_movieapp.core.data.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.dto.MovieDto
import com.prmto.mova_movieapp.core.data.dto.TvSeriesDto
import com.prmto.mova_movieapp.core.util.Constants
import com.prmto.mova_movieapp.feature_explore.data.multisearch.dto.SearchDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExploreApi {
    @GET("discover/movie")
    suspend fun discoverMovie(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("language") language: String,
        @Query("with_genres") genres: String = "",
        @Query("sort_by") sort: String
    ): Response<ApiResponse<MovieDto>>


    @GET("discover/tv")
    suspend fun discoverTv(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("language") language: String,
        @Query("with_genres") genres: String = "",
        @Query("sort_by") sort: String
    ): Response<ApiResponse<TvSeriesDto>>


    @GET("search/multi")
    suspend fun multiSearch(
        @Query("query") query: String,
        @Query("language") language: String,
        @Query("page") page: Int = Constants.STARTING_PAGE
    ): Response<ApiResponse<SearchDto>>
}