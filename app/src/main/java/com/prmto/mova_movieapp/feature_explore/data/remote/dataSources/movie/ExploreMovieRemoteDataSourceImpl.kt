package com.prmto.mova_movieapp.feature_explore.data.remote.dataSources.movie

import com.prmto.core_data.dispatcher.DispatcherProvider
import com.prmto.core_data.remote.dto.ApiResponse
import com.prmto.core_data.remote.dto.movie.MovieDto
import com.prmto.core_data.util.tryApiCall
import com.prmto.mova_movieapp.feature_explore.data.remote.api.ExploreApi
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExploreMovieRemoteDataSourceImpl @Inject constructor(
    private val exploreApi: ExploreApi,
    private val dispatcherProvider: DispatcherProvider
) : ExploreMovieRemoteDataSource {
    override suspend fun discoverMovie(
        page: Int,
        language: String,
        genres: String,
        sort: String
    ): ApiResponse<MovieDto> {
        return withContext(dispatcherProvider.IO) {
            tryApiCall { exploreApi.discoverMovie(page, language, genres, sort) }
        }
    }
}