package com.prmto.upcoming_data.remote.api

import com.prmto.core_data.remote.dto.movie.MovieDto
import com.prmto.core_data.util.Constants
import com.prmto.upcoming_data.remote.dto.UpComingApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UpcomingApi {

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("language") language: String
    ): Response<UpComingApiResponse<MovieDto>>
}