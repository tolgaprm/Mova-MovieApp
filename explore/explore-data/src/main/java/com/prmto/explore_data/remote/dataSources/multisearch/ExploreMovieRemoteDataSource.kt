package com.prmto.explore_data.remote.dataSources.multisearch

import com.prmto.core_data.remote.dto.ApiResponse
import com.prmto.core_data.util.Constants
import com.prmto.explore_data.remote.dto.multisearch.SearchDto

interface ExploreMultiSearchRemoteDataSource {

    suspend fun discoverMovie(
        query: String,
        language: String,
        page: Int = Constants.STARTING_PAGE
    ): ApiResponse<SearchDto>
}