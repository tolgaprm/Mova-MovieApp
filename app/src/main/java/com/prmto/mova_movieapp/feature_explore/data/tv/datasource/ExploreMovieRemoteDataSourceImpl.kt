package com.prmto.mova_movieapp.feature_explore.data.tv.datasource

import com.prmto.mova_movieapp.core.data.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.dto.TvSeriesDto
import com.prmto.mova_movieapp.core.data.tryApiCall
import com.prmto.mova_movieapp.feature_explore.data.api.ExploreApi
import javax.inject.Inject

class ExploreTvRemoteDataSourceImpl @Inject constructor(
    private val exploreApi: ExploreApi
) : ExploreTvRemoteDataSource {

    override suspend fun discoverTv(
        page: Int,
        language: String,
        genres: String,
        sort: String
    ): ApiResponse<TvSeriesDto> {
        return tryApiCall { exploreApi.discoverTv(page, language, genres, sort) }
    }
}