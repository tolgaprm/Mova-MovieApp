package com.prmto.mova_movieapp.feature_explore.data.remote.dataSources.tv

import com.prmto.core_data.dispatcher.DispatcherProvider
import com.prmto.core_data.remote.dto.ApiResponse
import com.prmto.core_data.remote.dto.tv.TvSeriesDto
import com.prmto.core_data.util.tryApiCall
import com.prmto.mova_movieapp.feature_explore.data.remote.api.ExploreApi
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExploreTvRemoteDataSourceImpl @Inject constructor(
    private val exploreApi: ExploreApi,
    private val dispatcherProvider: DispatcherProvider
) : ExploreTvRemoteDataSource {

    override suspend fun discoverTv(
        page: Int,
        language: String,
        genres: String,
        sort: String
    ): ApiResponse<TvSeriesDto> {
        return withContext(dispatcherProvider.IO) {
            tryApiCall { exploreApi.discoverTv(page, language, genres, sort) }
        }
    }
}