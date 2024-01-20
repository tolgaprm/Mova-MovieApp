package com.prmto.core_domain.fake.repository.local

import com.prmto.core_domain.repository.local.LocalDatabaseRepository
import com.prmto.core_domain.repository.local.MovieLocalRepository
import com.prmto.core_domain.repository.local.TvSeriesLocalRepository

class FakeLocalDatabaseRepository(
    override val movieLocalRepository: MovieLocalRepository,
    override val tvSeriesLocalRepository: TvSeriesLocalRepository = FakeTvSeriesLocalRepository(),
) : LocalDatabaseRepository {

    override suspend fun clearDatabase() {
        // do nothing
    }
}