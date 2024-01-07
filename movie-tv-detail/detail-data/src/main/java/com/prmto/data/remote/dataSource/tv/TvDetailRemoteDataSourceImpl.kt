package com.prmto.data.remote.dataSource.tv

import com.prmto.core_data.remote.dto.ApiResponse
import com.prmto.core_data.remote.dto.tv.TvSeriesDto
import com.prmto.core_data.util.tryApiCall
import com.prmto.core_domain.dispatcher.DispatcherProvider
import com.prmto.data.remote.api.DetailApi
import com.prmto.data.remote.dto.detail.tv.TvDetailDto
import com.prmto.data.remote.dto.detail.video.VideosDto
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TvDetailRemoteDataSourceImpl @Inject constructor(
    private val detailApi: DetailApi,
    private val dispatcherProvider: DispatcherProvider
) : TvDetailRemoteDataSource {
    override suspend fun getTvDetail(tvSeriesId: Int, language: String): TvDetailDto {
        return withContext(dispatcherProvider.IO) {
            tryApiCall {
                detailApi.getTvDetail(tvSeriesId, language)
            }
        }
    }

    override suspend fun getRecommendationsForTv(
        tvSeriesId: Int,
        language: String,
        page: Int
    ): ApiResponse<TvSeriesDto> {
        return withContext(dispatcherProvider.IO) {
            tryApiCall {
                detailApi.getRecommendationsForTv(tvSeriesId, language, page)
            }
        }
    }

    override suspend fun getTvVideos(tvSeriesId: Int, language: String): VideosDto {
        return withContext(dispatcherProvider.IO) {
            tryApiCall {
                detailApi.getTvVideos(tvSeriesId, language)
            }
        }
    }
}