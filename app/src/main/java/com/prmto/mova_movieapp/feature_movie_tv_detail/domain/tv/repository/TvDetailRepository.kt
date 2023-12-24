package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.tv.repository

import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.util.Resource
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.video.Videos
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.tv.model.TvDetail
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