package com.prmto.mova_movieapp.core.domain.use_case.tv

import com.prmto.mova_movieapp.core.domain.models.genre.Genre
import com.prmto.mova_movieapp.core.domain.repository.GenreRepository
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