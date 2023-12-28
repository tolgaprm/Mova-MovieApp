package com.prmto.mova_movieapp.feature_explore.data.remote.dataSources.multisearch

import com.prmto.mova_movieapp.core.data.dispatcher.DispatcherProvider
import com.prmto.mova_movieapp.core.data.remote.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.util.tryApiCall
import com.prmto.mova_movieapp.feature_explore.data.remote.api.ExploreApi
import com.prmto.mova_movieapp.feature_explore.data.remote.dto.multisearch.SearchDto
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExploreMultiSearchRemoteDataSourceImpl @Inject constructor(
    private val exploreApi: ExploreApi,
    private val dispatcherProvider: DispatcherProvider
) : ExploreMultiSearchRemoteDataSource {
    override suspend fun discoverMovie(
        query: String,
        language: String,
        page: Int
    ): ApiResponse<SearchDto> {
        return withContext(dispatcherProvider.IO) {
            tryApiCall { exploreApi.multiSearch(query, language, page) }
        }
    }
}