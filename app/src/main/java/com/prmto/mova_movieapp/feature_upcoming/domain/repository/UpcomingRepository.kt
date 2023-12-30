package com.prmto.mova_movieapp.feature_upcoming.domain.repository

import androidx.paging.PagingData
import com.prmto.database.entity.movie.UpcomingRemindEntity
import com.prmto.mova_movieapp.feature_upcoming.domain.model.UpcomingMovie
import kotlinx.coroutines.flow.Flow

interface UpcomingRepository {
    fun getUpComingMovies(
        language: String
    ): Flow<PagingData<UpcomingMovie>>

    suspend fun insertUpcomingRemind(upcomingRemindEntity: UpcomingRemindEntity)

    suspend fun deleteUpcomingRemind(upcomingRemindEntity: UpcomingRemindEntity)

    fun getAllUpcomingRemind(): Flow<List<UpcomingRemindEntity>>
}