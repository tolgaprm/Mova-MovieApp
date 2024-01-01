package com.prmto.explore_data.remote.dataSources.movie

import com.prmto.core_data.remote.dto.ApiResponse
import com.prmto.core_data.remote.dto.movie.MovieDto

interface ExploreMovieRemoteDataSource {

    suspend fun discoverMovie(
        page: Int,
        language: String,
        genres: String,
        sort: String
    ): ApiResponse<MovieDto>
}