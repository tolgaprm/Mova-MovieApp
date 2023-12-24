package com.prmto.mova_movieapp.feature_explore.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prmto.mova_movieapp.core.data.paging.getPagingMovies
import com.prmto.mova_movieapp.core.data.paging.getPagingTvSeries
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.presentation.util.toDiscoveryQueryString
import com.prmto.mova_movieapp.core.presentation.util.toSeparateWithComma
import com.prmto.mova_movieapp.core.util.Constants
import com.prmto.mova_movieapp.feature_explore.data.movie.datasource.ExploreMovieRemoteDataSource
import com.prmto.mova_movieapp.feature_explore.data.multisearch.MultiSearchPagingSource
import com.prmto.mova_movieapp.feature_explore.data.multisearch.datasource.ExploreMultiSearchRemoteDataSource
import com.prmto.mova_movieapp.feature_explore.data.multisearch.dto.SearchDto
import com.prmto.mova_movieapp.feature_explore.data.tv.datasource.ExploreTvRemoteDataSource
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

    override fun multiSearch(query: String, language: String): Flow<PagingData<SearchDto>> {
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