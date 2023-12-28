package com.prmto.mova_movieapp.core.domain.use_case.movie

import com.prmto.mova_movieapp.core.domain.models.genre.Genre
import com.prmto.mova_movieapp.core.domain.repository.GenreRepository
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