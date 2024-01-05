package com.prmto.database.entity.movie

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prmto.core_domain.models.movie.Movie
import com.prmto.database.util.Constants.MOVIE_WATCH_LIST_ITEM_TABLE_NAME

@Entity(tableName = MOVIE_WATCH_LIST_ITEM_TABLE_NAME)
data class MovieWatchListItem(
    @PrimaryKey val movieId: Int,
    @Embedded val movie: Movie,
)