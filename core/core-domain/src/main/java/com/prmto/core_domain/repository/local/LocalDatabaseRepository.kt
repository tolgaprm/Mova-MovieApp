package com.prmto.core_domain.repository.local

interface LocalDatabaseRepository {

    val movieLocalRepository: MovieLocalRepository
    val tvSeriesLocalRepository: TvSeriesLocalRepository
    suspend fun clearDatabase()
}