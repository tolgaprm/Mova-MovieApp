package com.prmto.authentication_domain.repository

import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.util.Resource

interface FirebaseTvSeriesRepository {

    suspend fun getFavoriteTvSeries(userUid: String): Resource<List<TvSeries>>

    suspend fun getTvSeriesInWatchList(userUid: String): Resource<List<TvSeries>>
}