package com.prmto.explore_data.remote.api

import com.prmto.core_data.remote.dto.ApiResponse
import com.prmto.core_data.remote.dto.movie.MovieDto
import com.prmto.core_data.remote.dto.tv.TvSeriesDto
import com.prmto.core_data.util.Constants
import com.prmto.explore_data.remote.dto.multisearch.SearchDto
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