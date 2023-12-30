package com.prmto.core_domain.use_case.movie

import com.prmto.core_domain.models.genre.Genre
import com.prmto.core_domain.repository.GenreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.yield
import javax.inject.Inject

class GetMovieGenreListUseCase @Inject constructor(
    private val repository: GenreRepository
) {

    operator fun invoke(language: String): Flow<List<Genre>> {
        return flow {
            yield()
            emit(repository.getMovieGenreList(language).genres)
        }
    }
}