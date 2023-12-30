package com.prmto.core_domain.use_case.database.tv

import com.prmto.core_domain.repository.local.LocalDatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteTvSeriesIdsUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository
) {
    operator fun invoke(): Flow<List<Int>> {
        return repository.tvSeriesLocalRepository.getFavoriteTvSeriesIds()
    }
}