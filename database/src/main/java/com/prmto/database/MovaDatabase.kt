package com.prmto.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prmto.database.converter.DatabaseConverter
import com.prmto.database.dao.movie.MovieDao
import com.prmto.database.dao.movie.UpComingDao
import com.prmto.database.dao.tv.TvSeriesDao
import com.prmto.database.entity.movie.FavoriteMovie
import com.prmto.database.entity.movie.MovieWatchListItem
import com.prmto.database.entity.movie.UpcomingRemindEntity
import com.prmto.database.entity.tv.FavoriteTvSeries
import com.prmto.database.entity.tv.TvSeriesWatchListItem

@Database(
    entities = [
        FavoriteMovie::class, MovieWatchListItem::class,
        FavoriteTvSeries::class, TvSeriesWatchListItem::class,
        UpcomingRemindEntity::class],
    exportSchema = false,
    version = 3
)
@TypeConverters(DatabaseConverter::class)
abstract class MovaDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao

    abstract val tvSeriesDao: TvSeriesDao

    abstract val upcomingDao: UpComingDao
}