package com.prmto.mova_movieapp.feature_explore.data.remote.dataSources.multisearch

import com.prmto.mova_movieapp.core.data.remote.dto.ApiResponse
import com.prmto.mova_movieapp.core.util.Constants
import com.prmto.mova_movieapp.feature_explore.data.remote.dto.multisearch.SearchDto

interface ExploreMultiSearchRemoteDataSource {

    suspend fun discoverMovie(
        query: String,
        language: String,
        page: Int = Constants.STARTING_PAGE
    ): ApiResponse<SearchDto>
}