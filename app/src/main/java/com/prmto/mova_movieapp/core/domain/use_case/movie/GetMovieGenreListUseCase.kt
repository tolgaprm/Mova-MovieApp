package com.prmto.mova_movieapp.core.domain.use_case.movie

import com.prmto.mova_movieapp.core.data.remote.dto.genre.Genre
import com.prmto.mova_movieapp.core.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieGenreListUseCase @Inject constructor(
    private val repository: RemoteRepository
) {

    operator fun invoke(language: String): Flow<List<Genre>> {
        return flow {
            emit(repository.getMovieGenreList(language).genres)
        }
    }
}