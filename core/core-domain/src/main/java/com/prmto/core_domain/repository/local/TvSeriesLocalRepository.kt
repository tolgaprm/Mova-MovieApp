package com.prmto.core_domain.repository.local

import com.prmto.core_domain.models.tv.TvSeries
import kotlinx.coroutines.flow.Flow

interface TvSeriesLocalRepository {
    suspend fun insertTvSeriesToFavoriteList(tvSeries: TvSeries)

    suspend fun insertTvSeriesToWatchListItem(tvSeries: TvSeries)

    suspend fun deleteTvSeriesFromFavoriteList(tvSeries: TvSeries)

    suspend fun deleteTvSeriesFromWatchListItem(tvSeries: TvSeries)

    fun getFavoriteTvSeriesIds(): Flow<List<Int>>

    fun getTvSeriesWatchListItemIds(): Flow<List<Int>>

    fun getFavoriteTvSeries(): Flow<List<TvSeries>>

    fun getTvSeriesInWatchList(): Flow<List<TvSeries>>
}