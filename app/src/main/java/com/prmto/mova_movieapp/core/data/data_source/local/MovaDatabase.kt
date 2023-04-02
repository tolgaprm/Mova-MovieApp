package com.prmto.mova_movieapp.core.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prmto.mova_movieapp.core.data.data_source.local.dao.MovieDao
import com.prmto.mova_movieapp.core.data.data_source.local.dao.TvSeriesDao
import com.prmto.mova_movieapp.core.data.data_source.local.models.movie.FavoriteMovie
import com.prmto.mova_movieapp.core.data.data_source.local.models.movie.MovieWatchListItem
import com.prmto.mova_movieapp.core.data.data_source.local.models.tv.FavoriteTvSeries
import com.prmto.mova_movieapp.core.data.data_source.local.models.tv.TvSeriesWatchListItem

@Database(
    entities = [FavoriteMovie::class, MovieWatchListItem::class, FavoriteTvSeries::class, TvSeriesWatchListItem::class],
    version = 1
)
@TypeConverters(DatabaseConverter::class)
abstract class MovaDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao

    abstract val tvSeriesDao: TvSeriesDao
}