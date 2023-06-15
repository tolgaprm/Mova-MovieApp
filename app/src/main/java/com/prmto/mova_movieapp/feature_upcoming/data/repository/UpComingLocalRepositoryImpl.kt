package com.prmto.mova_movieapp.feature_upcoming.data.repository

import com.prmto.mova_movieapp.core.data.data_source.local.MovaDatabase
import com.prmto.mova_movieapp.feature_upcoming.domain.model.UpcomingRemindEntity
import com.prmto.mova_movieapp.feature_upcoming.domain.repository.UpComingLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpComingLocalRepositoryImpl @Inject constructor(
    movaDatabase: MovaDatabase
) : UpComingLocalRepository {

    private val upComingDao = movaDatabase.upcomingDao

    override suspend fun insertUpcomingRemind(upcomingRemindEntity: UpcomingRemindEntity) {
        upComingDao.insertUpcomingRemind(upcomingRemindEntity)
    }

    override suspend fun deleteUpcomingRemind(upcomingRemindEntity: UpcomingRemindEntity) {
        upComingDao.deleteUpcomingRemind(upcomingRemindEntity)
    }

    override fun getAllUpcomingRemind(): Flow<List<UpcomingRemindEntity>> {
        return upComingDao.getUpcomingRemindList()
    }
}