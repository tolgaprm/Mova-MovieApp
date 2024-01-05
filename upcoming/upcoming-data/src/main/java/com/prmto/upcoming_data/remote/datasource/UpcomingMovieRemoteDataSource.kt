package com.prmto.upcoming_data.remote.datasource

import com.prmto.core_data.remote.dto.movie.MovieDto
import com.prmto.upcoming_data.remote.dto.UpComingApiResponse

interface UpcomingMovieRemoteDataSource {

    suspend fun getUpComingMovies(
        page: Int,
        language: String
    ): UpComingApiResponse<MovieDto>
}