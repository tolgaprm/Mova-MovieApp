package com.prmto.core_domain.use_case.movie

import com.prmto.core_domain.repository.local.LocalDatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieWatchListItemIdsUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository
) {
    operator fun invoke(): Flow<List<Int>> {
        return repository.movieLocalRepository.getMovieWatchListItemIds()
    }
}