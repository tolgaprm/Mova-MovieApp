package com.prmto.mova_movieapp.core.data.repository.local

import com.prmto.mova_movieapp.core.data.dispatcher.DispatcherProvider
import com.prmto.mova_movieapp.core.domain.repository.local.LocalDatabaseRepository
import com.prmto.mova_movieapp.core.domain.repository.local.MovieLocalRepository
import com.prmto.mova_movieapp.core.domain.repository.local.TvSeriesLocalRepository
import com.prmto.mova_movieapp.database.MovaDatabase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDatabaseRepositoryImpl @Inject constructor(
    private val database: MovaDatabase,
    override val movieLocalRepository: MovieLocalRepository,
    override val tvSeriesLocalRepository: TvSeriesLocalRepository,
    private val dispatcherProvider: DispatcherProvider
) : LocalDatabaseRepository {


    override suspend fun clearDatabase() {
        withContext(dispatcherProvider.IO) {
            database.clearAllTables()
        }
    }
}