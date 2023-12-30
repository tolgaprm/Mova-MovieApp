package com.prmto.mova_movieapp.feature_movie_tv_detail.data.repository.tv

import com.prmto.core_data.remote.mapper.tv.toTvSeries
import com.prmto.core_data.util.safeApiCallReturnResource
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.util.Resource
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dataSource.tv.TvDetailRemoteDataSource
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.mapper.detail.tv.toTvDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.mapper.video.toVideo
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.tv.TvDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.video.Videos
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.tv.TvDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TvDetailRepositoryImpl @Inject constructor(
    private val tvDetailRemoteDataSource: TvDetailRemoteDataSource
) : TvDetailRepository {
    override suspend fun getTvDetail(
        tvSeriesId: Int,
        language: String,
        countryIsoCode: String
    ): Resource<TvDetail> {
        return safeApiCallReturnResource {
            tvDetailRemoteDataSource.getTvDetail(tvSeriesId, language)
                .toTvDetail(countryIsoCode = countryIsoCode)
        }
    }

    override fun getRecommendationsForTv(
        tvSeriesId: Int,
        language: String,
        page: Int
    ): Flow<List<TvSeries>> {
        return flow {
            val listOfTv =
                tvDetailRemoteDataSource.getRecommendationsForTv(tvSeriesId, language, page)
                    .results.map { it.toTvSeries() }

            emit(listOfTv)
        }
    }

    override suspend fun getTvVideos(tvSeriesId: Int, language: String): Resource<Videos> {
        return safeApiCallReturnResource {
            tvDetailRemoteDataSource.getTvVideos(tvSeriesId, language)
                .toVideo()
        }
    }
}