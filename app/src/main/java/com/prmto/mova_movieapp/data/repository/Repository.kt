package com.prmto.mova_movieapp.data.repository

import com.prmto.mova_movieapp.domain.models.Genre
import com.prmto.mova_movieapp.domain.repository.RemoteRepository
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    suspend fun getMovieGenreList(language: String): List<Genre> {
        return remoteRepository.getMovieGenreList(language = language)
    }

    suspend fun getTvGenreList(language: String): List<Genre> {
        return remoteRepository.getTvGenreList(language = language)
    }
}