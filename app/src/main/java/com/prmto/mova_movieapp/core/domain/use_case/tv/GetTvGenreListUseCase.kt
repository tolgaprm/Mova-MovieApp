package com.prmto.mova_movieapp.core.domain.use_case.tv

import com.prmto.mova_movieapp.core.data.remote.dto.genre.Genre
import com.prmto.mova_movieapp.core.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetTvGenreListUseCase @Inject constructor(
    private val repository: RemoteRepository
) {
    operator fun invoke(language: String): Flow<List<Genre>> {
        return flow {
            emit(repository.getTvGenreList(language = language.lowercase()).genres)
        }
    }
}