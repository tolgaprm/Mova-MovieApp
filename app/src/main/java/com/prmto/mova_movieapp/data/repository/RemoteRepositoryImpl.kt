package com.prmto.mova_movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prmto.mova_movieapp.data.paging_source.APIFUNCTIONS
import com.prmto.mova_movieapp.data.paging_source.MoviesPagingSource
import com.prmto.mova_movieapp.data.paging_source.TvPagingSource
import com.prmto.mova_movieapp.data.remote.TMDBApi
import com.prmto.mova_movieapp.domain.models.GenreList
import com.prmto.mova_movieapp.domain.models.Movie
import com.prmto.mova_movieapp.domain.models.TvSeries
import com.prmto.mova_movieapp.domain.repository.RemoteRepository
import com.prmto.mova_movieapp.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val tmdbApi: TMDBApi
) : RemoteRepository {
    override suspend fun getMovieGenreList(language: String): GenreList {
        return tmdbApi.getMovieGenreList(language = language)
    }

    override suspend fun getTvGenreList(language: String): GenreList {
        return tmdbApi.getTvGenreList(language = language)
    }

    override fun getNowPlayingMovies(
        language: String,
        region: String
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    tmdbApi = tmdbApi,
                    language = language,
                    region = region,
                    apiFunc = APIFUNCTIONS.NOW_PLAYING_MOVIES
                )
            }
        ).flow
    }

    override fun getPopularMovies(language: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    tmdbApi = tmdbApi,
                    language = language,
                    apiFunc = APIFUNCTIONS.POPULAR_MOVIES
                )
            }
        ).flow
    }

    override fun getTopRatedMovies(language: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    tmdbApi = tmdbApi,
                    language = language,
                    apiFunc = APIFUNCTIONS.TOP_RATED_MOVIES
                )
            }
        ).flow
    }

    override fun getPopularTvs(language: String): Flow<PagingData<TvSeries>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                TvPagingSource(
                    tmdb = tmdbApi,
                    language = language
                )
            }
        ).flow
    }
}