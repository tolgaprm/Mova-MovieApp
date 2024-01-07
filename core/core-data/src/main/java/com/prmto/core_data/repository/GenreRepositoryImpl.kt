package com.prmto.core_data.repository

import com.prmto.core_data.remote.api.TMDBApi
import com.prmto.core_data.remote.mapper.genre.toGenreList
import com.prmto.core_domain.dispatcher.DispatcherProvider
import com.prmto.core_domain.models.genre.GenreList
import com.prmto.core_domain.repository.GenreRepository
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