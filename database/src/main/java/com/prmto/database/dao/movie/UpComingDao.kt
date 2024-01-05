package com.prmto.database.dao.movie

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prmto.database.entity.movie.UpcomingRemindEntity
import com.prmto.database.util.Constants.UPCOMING_REMIND_TABLE_NAME
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
