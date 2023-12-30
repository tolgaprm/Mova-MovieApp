package com.prmto.database.repository

import com.prmto.core_domain.repository.local.LocalDatabaseRepository
import com.prmto.core_domain.repository.local.MovieLocalRepository
import com.prmto.core_domain.repository.local.TvSeriesLocalRepository
import com.prmto.database.MovaDatabase
import javax.inject.Inject

class LocalDatabaseRepositoryImpl @Inject constructor(
    private val database: MovaDatabase,
    override val movieLocalRepository: MovieLocalRepository,
    override val tvSeriesLocalRepository: TvSeriesLocalRepository
) : LocalDatabaseRepository {


    override suspend fun clearDatabase() {
        database.clearAllTables()
    }
}