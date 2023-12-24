package com.prmto.mova_movieapp.feature_movie_tv_detail.data.tv.datasources

import com.prmto.mova_movieapp.core.data.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.dto.TvSeriesDto
import com.prmto.mova_movieapp.core.data.tryApiCall
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.api.DetailApi
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.tv.TvDetailDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.video.VideosDto
import javax.inject.Inject

class TvDetailRemoteDataSourceImpl @Inject constructor(
    private val detailApi: DetailApi
) : TvDetailRemoteDataSource {
    override suspend fun getTvDetail(tvSeriesId: Int, language: String): TvDetailDto {
        return tryApiCall {
            detailApi.getTvDetail(tvSeriesId, language)
        }
    }

    override suspend fun getRecommendationsForTv(
        tvSeriesId: Int,
        language: String,
        page: Int
    ): ApiResponse<TvSeriesDto> {
        return tryApiCall {
            detailApi.getRecommendationsForTv(tvSeriesId, language, page)
        }
    }

    override suspend fun getTvVideos(tvSeriesId: Int, language: String): VideosDto {
        return tryApiCall {
            detailApi.getTvVideos(tvSeriesId, language)
        }
    }
}