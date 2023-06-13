package com.prmto.mova_movieapp.feature_upcoming.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prmto.mova_movieapp.core.util.Constants.UPCOMING_REMIND_TABLE_NAME

@Entity(tableName = UPCOMING_REMIND_TABLE_NAME)
data class UpcomingRemindEntity(
    @PrimaryKey(autoGenerate = false) val movieId: Int,
    val movieTitle: String,
)
