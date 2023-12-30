package com.prmto.mova_movieapp.feature_home.data.remote.dataSources.tv

import com.prmto.core_data.remote.dto.ApiResponse
import com.prmto.core_data.remote.dto.tv.TvSeriesDto

interface HomeTvRemoteDataSource {

    suspend fun getPopularTvs(
        language: String,
        page: Int
    ): ApiResponse<TvSeriesDto>

    suspend fun getTopRatedTvs(
        language: String,
        page: Int
    ): ApiResponse<TvSeriesDto>
}