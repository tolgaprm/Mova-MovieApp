package com.prmto.mova_movieapp.core.data.repository

import com.prmto.mova_movieapp.core.data.remote.api.TMDBApi
import com.prmto.mova_movieapp.core.data.remote.dto.genre.GenreList
import com.prmto.mova_movieapp.core.domain.repository.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val tmdbApi: TMDBApi
) : RemoteRepository {
    override suspend fun getMovieGenreList(language: String): GenreList {

        return try {
            tmdbApi.getMovieGenreList(language = language)
        } catch (e: Exception) {
            throw e
        }


    }

    override suspend fun getTvGenreList(language: String): GenreList {
        return try {
            tmdbApi.getTvGenreList(language = language)
        } catch (e: Exception) {
            throw e
        }
    }
}