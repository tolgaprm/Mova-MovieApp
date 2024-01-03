package com.prmto.data.remote.dataSource.tv

import com.prmto.core_data.remote.dto.ApiResponse
import com.prmto.core_data.remote.dto.tv.TvSeriesDto
import com.prmto.data.remote.dto.detail.tv.TvDetailDto
import com.prmto.data.remote.dto.detail.video.VideosDto

interface TvDetailRemoteDataSource {

    suspend fun getTvDetail(
        tvSeriesId: Int,
        language: String
    ): TvDetailDto

    suspend fun getRecommendationsForTv(
        tvSeriesId: Int,
        language: String,
        page: Int = 1
    ): ApiResponse<TvSeriesDto>

    suspend fun getTvVideos(
        tvSeriesId: Int,
        language: String
    ): VideosDto
}