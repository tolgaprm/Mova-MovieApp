package com.prmto.mova_movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prmto.mova_movieapp.data.models.detail.movie.MovieDetailDto
import com.prmto.mova_movieapp.data.models.detail.tv.TvDetailDto
import com.prmto.mova_movieapp.data.models.enums.MoviesApiFunction
import com.prmto.mova_movieapp.data.models.enums.TvSeriesApiFunction
import com.prmto.mova_movieapp.data.paging_source.DiscoverMoviePagingSource
import com.prmto.mova_movieapp.data.paging_source.DiscoverTvPagingSource
import com.prmto.mova_movieapp.data.paging_source.MoviesPagingSource
import com.prmto.mova_movieapp.data.paging_source.TvPagingSource
import com.prmto.mova_movieapp.data.remote.TMDBApi
import com.prmto.mova_movieapp.domain.models.Movie
import com.prmto.mova_movieapp.domain.models.TvSeries
import com.prmto.mova_movieapp.domain.repository.RemoteRepository
import com.prmto.mova_movieapp.presentation.filter_bottom_sheet.state.FilterBottomState
import com.prmto.mova_movieapp.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val tmdbApi: TMDBApi
) : RemoteRepository {
    override suspend fun getMovieGenreList(language: String): com.prmto.mova_movieapp.data.models.GenreList {

        return try {
            tmdbApi.getMovieGenreList(language = language)
        } catch (e: Exception) {
            throw e
        }


    }

    override suspend fun getTvGenreList(language: String): com.prmto.mova_movieapp.data.models.GenreList {
        return try {
            tmdbApi.getTvGenreList(language = language)
        } catch (e: Exception) {
            throw e
        }

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
                    apiFunc = MoviesApiFunction.NOW_PLAYING_MOVIES
                )
            }
        ).flow
    }

    override fun getPopularMovies(language: String, region: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    tmdbApi = tmdbApi,
                    language = language,
                    apiFunc = MoviesApiFunction.POPULAR_MOVIES
                )
            }
        ).flow
    }

    override fun getTopRatedMovies(language: String, region: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    tmdbApi = tmdbApi,
                    language = language,
                    apiFunc = MoviesApiFunction.TOP_RATED_MOVIES
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
                    language = language,
                    apiFunction = TvSeriesApiFunction.POPULAR_TV
                )
            }
        ).flow
    }

    override fun getTopRatedTvs(language: String): Flow<PagingData<TvSeries>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                TvPagingSource(
                    tmdb = tmdbApi,
                    language = language,
                    apiFunction = TvSeriesApiFunction.TOP_RATED_TV
                )
            }
        ).flow
    }

    override fun discoverMovie(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                DiscoverMoviePagingSource(
                    tmdbApi = tmdbApi,
                    filterBottomState = filterBottomState,
                    language = language
                )
            }
        ).flow
    }

    override fun discoverTv(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<TvSeries>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                DiscoverTvPagingSource(
                    tmdbApi = tmdbApi,
                    filterBottomState = filterBottomState,
                    language = language
                )
            }
        ).flow
    }

    override suspend fun getMovieDetail(language: String, movieId: Int): MovieDetailDto {
        return tmdbApi.getMovieDetail(
            language = language,
            movieId = movieId
        )
    }

    override suspend fun getTvDetail(language: String, tvId: Int): TvDetailDto {
        return tmdbApi.getTvDetail(
            language = language,
            tvId = tvId
        )
    }
}