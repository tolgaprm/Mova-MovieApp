package com.prmto.core_domain.use_case.tv

import com.prmto.core_domain.models.genre.Genre
import com.prmto.core_domain.repository.GenreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetTvGenreListUseCase @Inject constructor(
    private val repository: GenreRepository
) {
    operator fun invoke(language: String): Flow<List<Genre>> {
        return flow {
            emit(repository.getTvGenreList(language = language.lowercase()).genres)
        }
    }
}