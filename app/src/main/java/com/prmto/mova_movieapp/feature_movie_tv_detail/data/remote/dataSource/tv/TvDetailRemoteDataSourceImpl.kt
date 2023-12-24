package com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dataSource.tv

import com.prmto.mova_movieapp.core.data.remote.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.remote.dto.tv.TvSeriesDto
import com.prmto.mova_movieapp.core.data.util.tryApiCall
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.api.DetailApi
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.detail.tv.TvDetailDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.detail.video.VideosDto
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