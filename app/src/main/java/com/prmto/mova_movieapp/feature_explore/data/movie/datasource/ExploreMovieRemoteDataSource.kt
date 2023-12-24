package com.prmto.mova_movieapp.feature_explore.data.movie.datasource

import com.prmto.mova_movieapp.core.data.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.dto.MovieDto

interface ExploreMovieRemoteDataSource {

    suspend fun discoverMovie(
        page: Int,
        language: String,
        genres: String,
        sort: String
    ): ApiResponse<MovieDto>
}