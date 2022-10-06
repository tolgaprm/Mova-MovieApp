package com.prmto.mova_movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prmto.mova_movieapp.data.paging_source.NowPlayingPagingSource
import com.prmto.mova_movieapp.data.remote.TMDBApi
import com.prmto.mova_movieapp.domain.models.Genre
import com.prmto.mova_movieapp.domain.models.Movie
import com.prmto.mova_movieapp.domain.repository.RemoteRepository
import com.prmto.mova_movieapp.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
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

    override suspend fun getNowPlayingMovies(
        language: String,
        region: String
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                NowPlayingPagingSource(
                    tmdbApi = tmdbApi,
                    language = language,
                    region = region
                )
            }
        ).flow
    }
}