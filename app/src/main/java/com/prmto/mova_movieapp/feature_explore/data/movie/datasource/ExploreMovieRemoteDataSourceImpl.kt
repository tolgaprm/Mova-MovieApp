package com.prmto.mova_movieapp.feature_explore.data.movie.datasource

import com.prmto.mova_movieapp.core.data.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.dto.MovieDto
import com.prmto.mova_movieapp.core.data.tryApiCall
import com.prmto.mova_movieapp.feature_explore.data.remote.ExploreApi
import javax.inject.Inject

class ExploreMovieRemoteDataSourceImpl @Inject constructor(
    private val exploreApi: ExploreApi
) : ExploreMovieRemoteDataSource {
    override suspend fun discoverMovie(
        page: Int,
        language: String,
        genres: String,
        sort: String
    ): ApiResponse<MovieDto> {
        return tryApiCall { exploreApi.discoverMovie(page, language, genres, sort) }
    }
}