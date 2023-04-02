package com.prmto.mova_movieapp.core.data.repository.local

import com.prmto.mova_movieapp.core.data.data_source.local.MovaDatabase
import com.prmto.mova_movieapp.core.domain.repository.local.LocalDatabaseRepository
import com.prmto.mova_movieapp.core.domain.repository.local.MovieLocalRepository
import com.prmto.mova_movieapp.core.domain.repository.local.TvSeriesLocalRepository
import javax.inject.Inject

class LocalDatabaseRepositoryImpl @Inject constructor(
    private val database: MovaDatabase,
    override val movieLocalRepository: MovieLocalRepository,
    override val tvSeriesLocalRepository: TvSeriesLocalRepository,
) : LocalDatabaseRepository {


    override suspend fun clearDatabase() {
        database.clearAllTables()
    }
}