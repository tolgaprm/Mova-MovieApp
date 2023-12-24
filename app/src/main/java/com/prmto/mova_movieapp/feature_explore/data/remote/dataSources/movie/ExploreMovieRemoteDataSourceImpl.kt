package com.prmto.mova_movieapp.feature_explore.data.remote.dataSources.movie

import com.prmto.mova_movieapp.core.data.remote.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.remote.dto.movie.MovieDto
import com.prmto.mova_movieapp.core.data.util.tryApiCall
import com.prmto.mova_movieapp.feature_explore.data.remote.api.ExploreApi
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