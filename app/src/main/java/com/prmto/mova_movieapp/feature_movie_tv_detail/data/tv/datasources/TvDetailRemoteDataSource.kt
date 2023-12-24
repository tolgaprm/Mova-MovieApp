package com.prmto.mova_movieapp.feature_movie_tv_detail.data.tv.datasources

import com.prmto.mova_movieapp.core.data.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.dto.TvSeriesDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.tv.TvDetailDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.video.VideosDto

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