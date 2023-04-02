package com.prmto.mova_movieapp.core.domain.repository.local

interface LocalDatabaseRepository {

    val movieLocalRepository: MovieLocalRepository
    val tvSeriesLocalRepository: TvSeriesLocalRepository
    suspend fun clearDatabase()
}