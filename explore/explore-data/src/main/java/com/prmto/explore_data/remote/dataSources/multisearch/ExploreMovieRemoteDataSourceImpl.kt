package com.prmto.explore_data.remote.dataSources.multisearch

import com.prmto.core_data.dispatcher.DispatcherProvider
import com.prmto.core_data.remote.dto.ApiResponse
import com.prmto.core_data.util.tryApiCall
import com.prmto.explore_data.remote.api.ExploreApi
import com.prmto.explore_data.remote.dto.multisearch.SearchDto
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