package com.prmto.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prmto.database.converter.DatabaseConverter
import com.prmto.database.dao.movie.MovieDao
import com.prmto.database.dao.tv.TvSeriesDao
import com.prmto.database.entity.movie.FavoriteMovie
import com.prmto.database.entity.movie.MovieWatchListItem
import com.prmto.database.entity.tv.FavoriteTvSeries
import com.prmto.database.entity.tv.TvSeriesWatchListItem

// TODO 1: Add UpcomingRemindEntity
// TODO 2: Add UpComingDao
@Database(
    entities = [
        FavoriteMovie::class, MovieWatchListItem::class,
        FavoriteTvSeries::class, TvSeriesWatchListItem::class],
    exportSchema = false,
    version = 3
)
@TypeConverters(DatabaseConverter::class)
abstract class MovaDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao

    abstract val tvSeriesDao: TvSeriesDao
}