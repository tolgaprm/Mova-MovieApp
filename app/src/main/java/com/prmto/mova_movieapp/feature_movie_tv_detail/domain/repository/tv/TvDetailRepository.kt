package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.tv

import com.prmto.mova_movieapp.core.domain.models.tv.TvSeries
import com.prmto.mova_movieapp.core.domain.util.Resource
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.tv.TvDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.video.Videos
import kotlinx.coroutines.flow.Flow

interface TvDetailRepository {
    suspend fun getTvDetail(
        tvSeriesId: Int,
        language: String,
        countryIsoCode: String
    ): Resource<TvDetail>

    fun getRecommendationsForTv(
        tvSeriesId: Int,
        language: String,
        page: Int = 1
    ): Flow<List<TvSeries>>

    suspend fun getTvVideos(
        tvSeriesId: Int,
        language: String
    ): Resource<Videos>
}