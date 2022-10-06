package com.prmto.mova_movieapp.domain.use_case.get_movie_genre_list

import com.prmto.mova_movieapp.domain.models.Genre
import com.prmto.mova_movieapp.domain.repository.RemoteRepository
import javax.inject.Inject

class GetMovieGenreListUseCase @Inject constructor(
    private val repository: RemoteRepository
) {

    suspend operator fun invoke(language: String): List<Genre> {
        return repository.getMovieGenreList(language = language)
    }
}