package com.prmto.explore_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prmto.core_data.util.Constants
import com.prmto.core_data.util.paging.getPagingMovies
import com.prmto.core_data.util.paging.getPagingTvSeries
import com.prmto.core_data.util.toDiscoveryQueryString
import com.prmto.core_data.util.toSeparateWithComma
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.explore_data.remote.dataSources.movie.ExploreMovieRemoteDataSource
import com.prmto.explore_data.remote.dataSources.multisearch.ExploreMultiSearchRemoteDataSource
import com.prmto.explore_data.remote.dataSources.tv.ExploreTvRemoteDataSource
import com.prmto.explore_domain.model.FilterBottomState
import com.prmto.explore_domain.repository.ExploreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExploreRepositoryImpl @Inject constructor(
    private val exploreMovieRemoteDataSource: ExploreMovieRemoteDataSource,
    private val exploreTvRemoteDataSource: ExploreTvRemoteDataSource,
    private val exploreMultiSearchRemoteDataSource: ExploreMultiSearchRemoteDataSource
) : ExploreRepository {

    override fun discoverMovie(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<Movie>> {
        return getPagingMovies { page ->
            exploreMovieRemoteDataSource.discoverMovie(
                page = page,
                language = language,
                genres = filterBottomState.checkedGenreIdsState.toSeparateWithComma(),
                sort = filterBottomState.checkedSortState.toDiscoveryQueryString(
                    filterBottomState.categoryState
                ),
            )
        }
    }

    override fun discoverTv(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<TvSeries>> {
        return getPagingTvSeries { page ->
            exploreTvRemoteDataSource.discoverTv(
                page = page,
                language = language,
                genres = filterBottomState.checkedGenreIdsState.toSeparateWithComma(),
                sort = filterBottomState.checkedSortState.toDiscoveryQueryString(
                    filterBottomState.categoryState
                ),
            )
        }
    }

    override fun multiSearch(
        query: String,
        language: String
    ): Flow<PagingData<com.prmto.explore_domain.model.MultiSearch>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                com.prmto.explore_data.remote.pagingSource.multisearch.MultiSearchPagingSource(
                    fetchMultiSearch = { page ->
                        exploreMultiSearchRemoteDataSource.discoverMovie(
                            query = query,
                            language = language,
                            page = page
                        ).results
                    }
                )
            }
        ).flow
    }
}