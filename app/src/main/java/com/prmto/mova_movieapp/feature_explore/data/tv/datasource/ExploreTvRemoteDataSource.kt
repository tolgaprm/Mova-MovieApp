package com.prmto.mova_movieapp.feature_explore.data.tv.datasource

import com.prmto.mova_movieapp.core.data.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.dto.TvSeriesDto

interface ExploreTvRemoteDataSource {

    suspend fun discoverTv(
        page: Int,
        language: String,
        genres: String,
        sort: String
    ): ApiResponse<TvSeriesDto>
}