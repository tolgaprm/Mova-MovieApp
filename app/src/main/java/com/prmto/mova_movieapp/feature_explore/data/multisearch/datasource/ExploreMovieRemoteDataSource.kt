package com.prmto.mova_movieapp.feature_explore.data.multisearch.datasource

import com.prmto.mova_movieapp.core.data.dto.ApiResponse
import com.prmto.mova_movieapp.core.util.Constants
import com.prmto.mova_movieapp.feature_explore.data.multisearch.dto.SearchDto

interface ExploreMultiSearchRemoteDataSource {

    suspend fun discoverMovie(
        query: String,
        language: String,
        page: Int = Constants.STARTING_PAGE
    ): ApiResponse<SearchDto>
}