package com.prmto.mova_movieapp.feature_explore.data.remote.dataSources.tv

import com.prmto.mova_movieapp.core.data.remote.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.remote.dto.tv.TvSeriesDto
import com.prmto.mova_movieapp.core.data.util.tryApiCall
import com.prmto.mova_movieapp.feature_explore.data.remote.api.ExploreApi
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