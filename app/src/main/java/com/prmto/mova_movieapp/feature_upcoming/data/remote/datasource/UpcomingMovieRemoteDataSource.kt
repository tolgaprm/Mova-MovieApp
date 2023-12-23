package com.prmto.mova_movieapp.feature_upcoming.data.remote.datasource

import com.prmto.mova_movieapp.core.data.dto.MovieDto
import com.prmto.mova_movieapp.feature_upcoming.data.remote.dto.UpComingApiResponse

interface UpcomingMovieRemoteDataSource {

    suspend fun getUpComingMovies(
        page: Int,
        language: String
    ): UpComingApiResponse<MovieDto>
}