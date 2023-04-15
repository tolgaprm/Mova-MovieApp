package com.prmto.mova_movieapp.feature_home.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.util.Constants
import com.prmto.mova_movieapp.feature_home.data.paging_source.MoviesPagingSource
import com.prmto.mova_movieapp.feature_home.data.paging_source.TvPagingSource
import com.prmto.mova_movieapp.feature_home.data.remote.HomeApi
import com.prmto.mova_movieapp.feature_home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi
) : HomeRepository {

    override fun getNowPlayingMovies(
        language: String,
        region: String
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    getMovies = { page ->
                        homeApi.getNowPlayingMovies(
                            page = page,
                            language = language,
                            region = region
                        )
                    }
                )
            }
        ).flow
    }

    override fun getPopularMovies(language: String, region: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    getMovies = { page ->
                        homeApi.getPopularMovies(
                            page = page,
                            language = language,
                            region = region
                        )
                    }
                )
            }
        ).flow
    }

    override fun getTopRatedMovies(language: String, region: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    getMovies = { page ->
                        homeApi.getTopRatedMovies(
                            page = page,
                            language = language,
                            region = region
                        )
                    }
                )
            }
        ).flow
    }

    override fun getPopularTvs(language: String): Flow<PagingData<TvSeries>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                TvPagingSource(
                    getTvSeries = { page ->
                        homeApi.getPopularTvs(
                            page = page,
                            language = language
                        )
                    }
                )
            }
        ).flow
    }

    override fun getTopRatedTvs(language: String): Flow<PagingData<TvSeries>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                TvPagingSource(
                    getTvSeries = { page ->
                        homeApi.getTopRatedTvs(
                            page = page,
                            language = language
                        )
                    }
                )
            }
        ).flow
    }

}