package com.prmto.mova_movieapp.core.data.local.entity.movie

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.util.Constants.MOVIE_WATCH_LIST_ITEM_TABLE_NAME


@Entity(tableName = MOVIE_WATCH_LIST_ITEM_TABLE_NAME)
data class MovieWatchListItem(
    @PrimaryKey val movieId: Int,
    @Embedded val movie: Movie,
)