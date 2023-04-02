package com.prmto.mova_movieapp.core.domain.use_case

import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.repository.local.LocalDatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesInWatchListUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository
) {

    operator fun invoke(): Flow<List<Movie>> {
        return repository.movieLocalRepository.getMoviesInWatchList()
    }
}