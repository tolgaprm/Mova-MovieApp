package com.prmto.database.entity.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prmto.database.util.Constants.UPCOMING_REMIND_TABLE_NAME

@Entity(tableName = UPCOMING_REMIND_TABLE_NAME)
data class UpcomingRemindEntity(
    @PrimaryKey(autoGenerate = false) val movieId: Int,
    val movieTitle: String,
    val movieReleaseDate: String,
)