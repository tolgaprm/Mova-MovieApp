package com.prmto.mova_movieapp.feature_home.data.remote.dataSources.tv

import com.prmto.mova_movieapp.core.data.dispatcher.DispatcherProvider
import com.prmto.mova_movieapp.core.data.remote.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.remote.dto.tv.TvSeriesDto
import com.prmto.mova_movieapp.core.data.util.tryApiCall
import com.prmto.mova_movieapp.feature_home.data.remote.api.HomeApi
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeTvRemoteDataSourceImpl @Inject constructor(
    private val homeApi: HomeApi,
    private val dispatcherProvider: DispatcherProvider
) : HomeTvRemoteDataSource {
    override suspend fun getPopularTvs(language: String, page: Int): ApiResponse<TvSeriesDto> {
        return withContext(dispatcherProvider.IO) {
            tryApiCall {
                homeApi.getPopularTvs(
                    language = language,
                    page = page
                )
            }
        }
    }

    override suspend fun getTopRatedTvs(language: String, page: Int): ApiResponse<TvSeriesDto> {
        return withContext(dispatcherProvider.IO) {
            tryApiCall {
                homeApi.getTopRatedTvs(
                    language = language,
                    page = page
                )
            }
        }
    }
}
