package com.prmto.mova_movieapp.domain.use_case.get_tv_genre_list

import com.prmto.mova_movieapp.data.repository.Repository
import com.prmto.mova_movieapp.domain.models.Genre
import javax.inject.Inject


class GetTvGenreList @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(language: String): List<Genre> {
        return repository.getTvGenreList(language = language)
    }
}