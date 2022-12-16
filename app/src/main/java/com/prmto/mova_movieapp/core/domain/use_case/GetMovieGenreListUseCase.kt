package com.prmto.mova_movieapp.core.domain.use_case

import com.prmto.mova_movieapp.core.data.dto.GenreList
import com.prmto.mova_movieapp.core.domain.repository.RemoteRepository
import javax.inject.Inject

class GetMovieGenreListUseCase @Inject constructor(
    private val repository: RemoteRepository
) {

    suspend operator fun invoke(language: String): GenreList {
        return repository.getMovieGenreList(language = language.lowercase())
    }
}