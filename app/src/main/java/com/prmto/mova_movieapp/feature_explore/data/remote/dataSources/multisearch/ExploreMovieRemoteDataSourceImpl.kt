package com.prmto.mova_movieapp.feature_explore.data.remote.dataSources.multisearch

import com.prmto.mova_movieapp.core.data.remote.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.util.tryApiCall
import com.prmto.mova_movieapp.feature_explore.data.remote.api.ExploreApi
import com.prmto.mova_movieapp.feature_explore.data.remote.dto.multisearch.SearchDto
import javax.inject.Inject

class ExploreMultiSearchRemoteDataSourceImpl @Inject constructor(
    private val exploreApi: ExploreApi
) : ExploreMultiSearchRemoteDataSource {
    override suspend fun discoverMovie(
        query: String,
        language: String,
        page: Int
    ): ApiResponse<SearchDto> {
        return tryApiCall { exploreApi.multiSearch(query, language, page) }
    }
}