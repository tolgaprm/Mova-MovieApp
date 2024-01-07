package com.prmto.core_domain.repository.firebase

import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.util.SimpleResource

interface FirebaseCoreTvSeriesRepository {
    suspend fun addTvSeriesToFavoriteList(
        userUid: String,
        tvSeriesInFavoriteList: List<TvSeries>
    ): SimpleResource

    suspend fun addTvSeriesToWatchList(
        userUid: String,
        tvSeriesInWatchList: List<TvSeries>
    ): SimpleResource
}