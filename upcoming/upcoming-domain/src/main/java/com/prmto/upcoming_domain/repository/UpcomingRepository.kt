package com.prmto.upcoming_domain.repository

import androidx.paging.PagingData
import com.prmto.database.entity.movie.UpcomingRemindEntity
import com.prmto.upcoming_domain.model.UpcomingMovie
import kotlinx.coroutines.flow.Flow

interface UpcomingRepository {
    fun getUpComingMovies(
        language: String
    ): Flow<PagingData<UpcomingMovie>>

    suspend fun insertUpcomingRemind(upcomingMovie: UpcomingMovie)

    suspend fun deleteUpcomingRemind(upcomingMovie: UpcomingMovie)

    fun getAllUpcomingRemind(): Flow<List<UpcomingRemindEntity>>
}