package com.prmto.mova_movieapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prmto.mova_movieapp.core.data.local.converter.DatabaseConverter
import com.prmto.mova_movieapp.core.data.local.dao.movie.MovieDao
import com.prmto.mova_movieapp.core.data.local.dao.tv.TvSeriesDao
import com.prmto.mova_movieapp.core.data.local.entity.movie.FavoriteMovie
import com.prmto.mova_movieapp.core.data.local.entity.movie.MovieWatchListItem
import com.prmto.mova_movieapp.core.data.local.entity.tv.FavoriteTvSeries
import com.prmto.mova_movieapp.core.data.local.entity.tv.TvSeriesWatchListItem
import com.prmto.mova_movieapp.feature_upcoming.data.local.dao.UpComingDao
import com.prmto.mova_movieapp.feature_upcoming.domain.model.UpcomingRemindEntity

@Database(
    entities = [
        FavoriteMovie::class, MovieWatchListItem::class,
        FavoriteTvSeries::class, TvSeriesWatchListItem::class,
        UpcomingRemindEntity::class
    ],
    exportSchema = false,
    version = 2
)
@TypeConverters(DatabaseConverter::class)
abstract class MovaDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao

    abstract val tvSeriesDao: TvSeriesDao

    abstract val upcomingDao: UpComingDao
}