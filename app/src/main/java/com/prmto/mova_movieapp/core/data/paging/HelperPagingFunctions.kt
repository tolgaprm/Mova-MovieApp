package com.prmto.mova_movieapp.core.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prmto.mova_movieapp.core.data.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.dto.MovieDto
import com.prmto.mova_movieapp.core.data.dto.TvSeriesDto
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.util.Constants
import com.prmto.mova_movieapp.feature_home.data.movie.MoviesPagingSource
import com.prmto.mova_movieapp.feature_home.data.tv.TvPagingSource
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