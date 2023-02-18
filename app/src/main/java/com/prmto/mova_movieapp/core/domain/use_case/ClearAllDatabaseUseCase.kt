package com.prmto.mova_movieapp.core.domain.use_case

import com.prmto.mova_movieapp.core.domain.repository.LocalDatabaseRepository
import javax.inject.Inject

class ClearAllDatabaseUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository
) {
    suspend operator fun invoke() {
        repository.deleteMovieFavoriteTable()
        repository.deleteMovieWatchTable()
        repository.deleteTvSeriesFavoriteTable()
        repository.deleteTvSeriesWatchTable()
    }
}