package com.prmto.mova_movieapp.feature_explore.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prmto.mova_movieapp.core.data.util.paging.getPagingMovies
import com.prmto.mova_movieapp.core.data.util.paging.getPagingTvSeries
import com.prmto.mova_movieapp.core.data.util.toDiscoveryQueryString
import com.prmto.mova_movieapp.core.data.util.toSeparateWithComma
import com.prmto.mova_movieapp.core.domain.models.movie.Movie
import com.prmto.mova_movieapp.core.domain.models.tv.TvSeries
import com.prmto.mova_movieapp.core.util.Constants
import com.prmto.mova_movieapp.feature_explore.data.remote.dataSources.movie.ExploreMovieRemoteDataSource
import com.prmto.mova_movieapp.feature_explore.data.remote.dataSources.multisearch.ExploreMultiSearchRemoteDataSource
import com.prmto.mova_movieapp.feature_explore.data.remote.dataSources.tv.ExploreTvRemoteDataSource
import com.prmto.mova_movieapp.feature_explore.data.remote.pagingSource.multisearch.MultiSearchPagingSource
import com.prmto.mova_movieapp.feature_explore.domain.model.MultiSearch
import com.prmto.mova_movieapp.feature_explore.domain.repository.ExploreRepository
import com.prmto.mova_movieapp.feature_explore.presentation.filter_bottom_sheet.state.FilterBottomState
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

    override fun multiSearch(query: String, language: String): Flow<PagingData<MultiSearch>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                MultiSearchPagingSource(
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