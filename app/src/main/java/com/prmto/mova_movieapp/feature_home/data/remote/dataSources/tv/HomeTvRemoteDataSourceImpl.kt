package com.prmto.mova_movieapp.feature_home.data.remote.dataSources.tv

import com.prmto.mova_movieapp.core.data.remote.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.remote.dto.tv.TvSeriesDto
import com.prmto.mova_movieapp.core.data.util.tryApiCall
import com.prmto.mova_movieapp.feature_home.data.remote.api.HomeApi
import javax.inject.Inject

class HomeTvRemoteDataSourceImpl @Inject constructor(
    private val homeApi: HomeApi
) : HomeTvRemoteDataSource {
    override suspend fun getPopularTvs(language: String, page: Int): ApiResponse<TvSeriesDto> {
        return tryApiCall {
            homeApi.getPopularTvs(
                language = language,
                page = page
            )
        }
    }

    override suspend fun getTopRatedTvs(language: String, page: Int): ApiResponse<TvSeriesDto> {
        return tryApiCall {
            homeApi.getTopRatedTvs(
                language = language,
                page = page
            )
        }
    }
}
