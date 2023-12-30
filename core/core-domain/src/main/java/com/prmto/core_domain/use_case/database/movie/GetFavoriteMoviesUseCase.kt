package com.prmto.core_domain.use_case.database.movie

import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.repository.local.LocalDatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository,
) {

    operator fun invoke(): Flow<List<Movie>> {
        return repository.movieLocalRepository.getFavoriteMovies()
    }
}