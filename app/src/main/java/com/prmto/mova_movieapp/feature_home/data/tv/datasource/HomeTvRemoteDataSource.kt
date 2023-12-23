package com.prmto.mova_movieapp.feature_home.data.tv.datasource

import com.prmto.mova_movieapp.core.data.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.dto.TvSeriesDto

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