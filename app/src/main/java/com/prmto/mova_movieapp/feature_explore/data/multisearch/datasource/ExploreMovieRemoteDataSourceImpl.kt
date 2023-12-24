package com.prmto.mova_movieapp.feature_explore.data.multisearch.datasource

import com.prmto.mova_movieapp.core.data.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.tryApiCall
import com.prmto.mova_movieapp.feature_explore.data.api.ExploreApi
import com.prmto.mova_movieapp.feature_explore.data.multisearch.dto.SearchDto
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