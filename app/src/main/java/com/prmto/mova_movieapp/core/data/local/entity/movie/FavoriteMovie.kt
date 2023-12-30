package com.prmto.mova_movieapp.core.data.local.entity.movie

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prmto.mova_movieapp.core.data.util.Constants.FAVORITE_MOVIE_TABLE_NAME
import com.prmto.mova_movieapp.core.domain.models.movie.Movie

@Entity(tableName = FAVORITE_MOVIE_TABLE_NAME)
data class FavoriteMovie(
    @PrimaryKey val movieId: Int,
    @Embedded val movie: Movie,
)