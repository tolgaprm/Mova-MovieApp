package com.prmto.mova_movieapp.core.data.repository.local

import com.prmto.mova_movieapp.core.data.remote.mapper.tv.toFavoriteTvSeries
import com.prmto.mova_movieapp.core.data.remote.mapper.tv.toTvSeriesWatchListItem
import com.prmto.mova_movieapp.core.domain.models.tv.TvSeries
import com.prmto.mova_movieapp.core.domain.repository.local.TvSeriesLocalRepository
import com.prmto.mova_movieapp.database.MovaDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TvSeriesLocalRepositoryImpl @Inject constructor(
    private val movaDatabase: MovaDatabase,
) : TvSeriesLocalRepository {

    private val tvSeriesDao = movaDatabase.tvSeriesDao

    override suspend fun insertTvSeriesToFavoriteList(tvSeries: TvSeries) {
        tvSeriesDao.insertTvSeriesToFavoriteList(
            favoriteTvSeries = tvSeries.toFavoriteTvSeries()
        )
    }

    override suspend fun insertTvSeriesToWatchListItem(tvSeries: TvSeries) {
        tvSeriesDao.insertTvSeriesToWatchListItem(
            tvSeriesWatchListItem = tvSeries.toTvSeriesWatchListItem()
        )
    }

    override suspend fun deleteTvSeriesFromFavoriteList(tvSeries: TvSeries) {
        tvSeriesDao.deleteTvSeriesFromFavoriteList(
            favoriteTvSeries = tvSeries.toFavoriteTvSeries()
        )
    }

    override suspend fun deleteTvSeriesFromWatchListItem(tvSeries: TvSeries) {
        tvSeriesDao.deleteTvSeriesFromWatchListItem(
            tvSeriesWatchListItem = tvSeries.toTvSeriesWatchListItem()
        )
    }

    override fun getFavoriteTvSeriesIds(): Flow<List<Int>> {
        return tvSeriesDao.getFavoriteTvSeriesIds()
    }

    override fun getTvSeriesWatchListItemIds(): Flow<List<Int>> {
        return tvSeriesDao.getTvSeriesWatchListItemIds()
    }

    override fun getFavoriteTvSeries(): Flow<List<TvSeries>> {
        return tvSeriesDao.getFavoriteTvSeries()
            .map { it.map { it.tvSeries } }
    }

    override fun getTvSeriesInWatchList(): Flow<List<TvSeries>> {
        return tvSeriesDao.getTvSeriesWatchList()
            .map { it.map { it.tvSeries } }
    }
}