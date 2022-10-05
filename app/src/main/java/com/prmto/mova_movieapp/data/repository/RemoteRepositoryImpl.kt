package com.prmto.mova_movieapp.data.repository

import com.prmto.mova_movieapp.data.remote.TMDBApi
import com.prmto.mova_movieapp.domain.models.Genre
import com.prmto.mova_movieapp.domain.repository.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val tmdbApi: TMDBApi
) : RemoteRepository {
    override suspend fun getMovieGenreList(language: String): List<Genre> {
        return tmdbApi.getMovieGenreList(language = language)
    }

    override suspend fun getTvGenreList(language: String): List<Genre> {
        return tmdbApi.getTvGenreList(language = language)
    }
}