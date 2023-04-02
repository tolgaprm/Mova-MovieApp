package com.prmto.mova_movieapp.core.data.data_source.local.models.movie

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.util.Constants.FAVORITE_MOVIE_TABLE_NAME

@Entity(tableName = FAVORITE_MOVIE_TABLE_NAME)
data class FavoriteMovie(
    @PrimaryKey val movieId: Int,
    @Embedded val movie: Movie,
)