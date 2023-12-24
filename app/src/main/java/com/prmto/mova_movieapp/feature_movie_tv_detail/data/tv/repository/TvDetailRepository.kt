package com.prmto.mova_movieapp.feature_movie_tv_detail.data.tv.repository

import com.prmto.mova_movieapp.core.data.mapper.toTvSeries
import com.prmto.mova_movieapp.core.data.safeApiCallReturnResource
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.util.Resource
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.mapper.toTvDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.mapper.toVideo
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.tv.datasources.TvDetailRemoteDataSource
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.video.Videos
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.tv.model.TvDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.tv.repository.TvDetailRepository
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