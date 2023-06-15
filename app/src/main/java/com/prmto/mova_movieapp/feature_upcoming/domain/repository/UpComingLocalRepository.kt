package com.prmto.mova_movieapp.feature_upcoming.domain.repository

import com.prmto.mova_movieapp.feature_upcoming.domain.model.UpcomingRemindEntity
import kotlinx.coroutines.flow.Flow

interface UpComingLocalRepository {

    suspend fun insertUpcomingRemind(upcomingRemindEntity: UpcomingRemindEntity)

    suspend fun deleteUpcomingRemind(upcomingRemindEntity: UpcomingRemindEntity)

    fun getAllUpcomingRemind(): Flow<List<UpcomingRemindEntity>>
}