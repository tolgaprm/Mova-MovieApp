package com.prmto.mova_movieapp.feature_upcoming.data.remote

import com.prmto.mova_movieapp.core.data.dto.MovieDto
import com.prmto.mova_movieapp.core.util.Constants
import com.prmto.mova_movieapp.feature_upcoming.data.dto.UpComingApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UpComingApi {

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("language") language: String
    ): UpComingApiResponse<MovieDto>
}