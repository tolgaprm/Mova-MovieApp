package com.prmto.mova_movieapp.feature_explore.data.remote.dataSources.movie

import com.prmto.mova_movieapp.core.data.remote.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.remote.dto.movie.MovieDto

interface ExploreMovieRemoteDataSource {

    suspend fun discoverMovie(
        page: Int,
        language: String,
        genres: String,
        sort: String
    ): ApiResponse<MovieDto>
}