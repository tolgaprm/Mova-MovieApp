package com.prmto.home_data.remote.dataSources.tv

import com.prmto.core_data.remote.dto.ApiResponse
import com.prmto.core_data.remote.dto.tv.TvSeriesDto
import com.prmto.core_data.util.tryApiCall
import com.prmto.core_domain.dispatcher.DispatcherProvider
import com.prmto.home_data.remote.api.HomeApi
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
