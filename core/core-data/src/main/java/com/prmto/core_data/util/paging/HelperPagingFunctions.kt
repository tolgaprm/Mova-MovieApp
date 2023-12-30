package com.prmto.core_data.util.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prmto.core_data.remote.dto.ApiResponse
import com.prmto.core_data.remote.dto.movie.MovieDto
import com.prmto.core_data.remote.dto.tv.TvSeriesDto
import com.prmto.core_data.remote.pagingSource.movie.MoviesPagingSource
import com.prmto.core_data.remote.pagingSource.tv.TvPagingSource
import com.prmto.core_data.util.Constants
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.models.tv.TvSeries
import kotlinx.coroutines.flow.Flow

inline fun getPagingTvSeries(
    crossinline tvFetcher: suspend (page: Int) -> ApiResponse<TvSeriesDto>
): Flow<PagingData<TvSeries>> {
    return Pager(
        config = PagingConfig(
            pageSize = Constants.ITEMS_PER_PAGE,
        ),
        pagingSourceFactory = {
            TvPagingSource(
                fetchTvSeries = { page ->
                    tvFetcher(page).results
                }
            )
        }
    ).flow
}

inline fun getPagingMovies(
    crossinline movieFetcher: suspend (page: Int) -> ApiResponse<MovieDto>
): Flow<PagingData<Movie>> {
    return Pager(
        config = PagingConfig(
            pageSize = Constants.ITEMS_PER_PAGE,
        ),
        pagingSourceFactory = {
            MoviesPagingSource(
                fetchMovie = { page ->
                    movieFetcher(page).results
                }
            )
        }
    ).flow
}