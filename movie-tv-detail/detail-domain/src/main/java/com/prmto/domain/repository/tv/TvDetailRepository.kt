package com.prmto.domain.repository.tv

import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.util.Resource
import com.prmto.domain.models.detail.tv.TvDetail
import com.prmto.domain.models.detail.video.Videos
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