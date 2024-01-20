package com.prmto.core_domain.fake.repository.local

import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.repository.local.TvSeriesLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTvSeriesLocalRepository : TvSeriesLocalRepository {
    private val favoriteTvSeries = mutableListOf<TvSeries>()
    private val watchListTvSeries = mutableListOf<TvSeries>()
    override suspend fun insertTvSeriesToFavoriteList(tvSeries: TvSeries) {
        favoriteTvSeries.add(tvSeries)
    }

    override suspend fun insertTvSeriesToWatchListItem(tvSeries: TvSeries) {
        watchListTvSeries.add(tvSeries)
    }

    override suspend fun deleteTvSeriesFromFavoriteList(tvSeries: TvSeries) {
        if (tvSeries in favoriteTvSeries) {
            favoriteTvSeries.remove(tvSeries)
        }
    }

    override suspend fun deleteTvSeriesFromWatchListItem(tvSeries: TvSeries) {
        if (tvSeries in watchListTvSeries) {
            watchListTvSeries.remove(tvSeries)
        }
    }

    override fun getFavoriteTvSeriesIds(): Flow<List<Int>> {
        val favoriteTvSeriesIds = favoriteTvSeries.map { it.id }
        return flow { emit(favoriteTvSeriesIds) }
    }

    override fun getTvSeriesWatchListItemIds(): Flow<List<Int>> {
        val watchListTvSeriesIds = watchListTvSeries.map { it.id }
        return flow { emit(watchListTvSeriesIds) }
    }

    override fun getFavoriteTvSeries(): Flow<List<TvSeries>> {
        return flow { emit(favoriteTvSeries) }
    }

    override fun getTvSeriesInWatchList(): Flow<List<TvSeries>> {
        return flow { emit(watchListTvSeries) }
    }
}