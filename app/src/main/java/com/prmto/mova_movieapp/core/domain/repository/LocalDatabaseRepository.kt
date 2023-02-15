package com.prmto.mova_movieapp.core.domain.repository

import com.prmto.mova_movieapp.core.domain.models.FavoriteMovie
import com.prmto.mova_movieapp.core.domain.models.FavoriteTvSeries
import com.prmto.mova_movieapp.core.domain.models.MovieWatchListItem
import com.prmto.mova_movieapp.core.domain.models.TvSeriesWatchListItem
import kotlinx.coroutines.flow.Flow

interface LocalDatabaseRepository {

    // MOVIES
    suspend fun insertMovieToFavoriteList(favoriteMovie: FavoriteMovie)

    suspend fun insertMovieToWatchList(movieWatchListItem: MovieWatchListItem)

    suspend fun deleteMovieFromFavoriteList(favoriteMovie: FavoriteMovie)

    suspend fun deleteMovieFromWatchListItem(movieWatchListItem: MovieWatchListItem)

    fun getFavoriteMovieIds(): Flow<List<Int>>

    fun getMovieWatchListItemIds(): Flow<List<Int>>

    // TVSERIES
    suspend fun insertTvSeriesToFavoriteList(favoriteTvSeries: FavoriteTvSeries)

    suspend fun insertTvSeriesToWatchListItem(tvSeriesWatchListItem: TvSeriesWatchListItem)

    suspend fun deleteTvSeriesFromFavoriteList(favoriteTvSeries: FavoriteTvSeries)

    suspend fun deleteTvSeriesFromWatchListItem(tvSeriesWatchListItem: TvSeriesWatchListItem)

    fun getFavoriteTvSeriesIds(): Flow<List<Int>>

    fun getTvSeriesWatchListItemIds(): Flow<List<Int>>

    fun getFavoriteTvSeries(): Flow<List<FavoriteTvSeries>>

    fun getTvSeriesWatchList(): Flow<List<TvSeriesWatchListItem>>

}