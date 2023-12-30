package com.prmto.mova_movieapp.feature_upcoming.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prmto.mova_movieapp.core.data.util.Constants.UPCOMING_REMIND_TABLE_NAME
import com.prmto.mova_movieapp.feature_upcoming.domain.model.UpcomingRemindEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UpComingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcomingRemind(upcomingRemindEntity: UpcomingRemindEntity)

    @Delete
    suspend fun deleteUpcomingRemind(upcomingRemindEntity: UpcomingRemindEntity)

    @Query("SELECT * FROM $UPCOMING_REMIND_TABLE_NAME")
    fun getUpcomingRemindList(): Flow<List<UpcomingRemindEntity>>
}
