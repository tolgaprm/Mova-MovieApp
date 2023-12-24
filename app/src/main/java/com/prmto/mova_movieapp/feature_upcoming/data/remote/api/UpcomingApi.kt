package com.prmto.mova_movieapp.feature_upcoming.data.remote.api

import com.prmto.mova_movieapp.core.data.remote.dto.movie.MovieDto
import com.prmto.mova_movieapp.core.util.Constants
import com.prmto.mova_movieapp.feature_upcoming.data.remote.dto.UpComingApiResponse
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