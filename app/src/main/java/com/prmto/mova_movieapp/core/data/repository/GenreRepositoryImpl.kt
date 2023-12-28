package com.prmto.mova_movieapp.core.data.repository

import com.prmto.mova_movieapp.core.data.dispatcher.DispatcherProvider
import com.prmto.mova_movieapp.core.data.remote.api.TMDBApi
import com.prmto.mova_movieapp.core.data.remote.mapper.genre.toGenreList
import com.prmto.mova_movieapp.core.domain.models.genre.GenreList
import com.prmto.mova_movieapp.core.domain.repository.GenreRepository
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val tmdbApi: TMDBApi,
    private val dispatcherProvider: DispatcherProvider
) : GenreRepository {
    override suspend fun getMovieGenreList(language: String): GenreList {
        return withContext(dispatcherProvider.IO) {
            try {
                tmdbApi.getMovieGenreList(language = language).toGenreList()
            } catch (e: Exception) {
                Timber.d(e.message.toString())
                GenreList(emptyList())
            }
        }
    }

    override suspend fun getTvGenreList(language: String): GenreList {
        return withContext(dispatcherProvider.IO) {
            try {
                tmdbApi.getTvGenreList(language = language).toGenreList()
            } catch (e: Exception) {
                Timber.d(e.message.toString())
                GenreList(emptyList())
            }
        }
    }
}