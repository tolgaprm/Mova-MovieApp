package com.prmto.mova_movieapp.core.domain.use_case

import com.prmto.mova_movieapp.core.data.dto.GenreList
import com.prmto.mova_movieapp.core.domain.repository.RemoteRepository
import javax.inject.Inject


class GetTvGenreListUseCase @Inject constructor(
    private val repository: RemoteRepository
) {

    suspend operator fun invoke(language: String): GenreList {
        return repository.getTvGenreList(language = language.lowercase())
    }
}