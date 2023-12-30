package com.prmto.mova_movieapp.feature_explore.data.remote.dataSources.tv

import com.prmto.core_data.remote.dto.ApiResponse
import com.prmto.core_data.remote.dto.tv.TvSeriesDto

interface ExploreTvRemoteDataSource {

    suspend fun discoverTv(
        page: Int,
        language: String,
        genres: String,
        sort: String
    ): ApiResponse<TvSeriesDto>
}