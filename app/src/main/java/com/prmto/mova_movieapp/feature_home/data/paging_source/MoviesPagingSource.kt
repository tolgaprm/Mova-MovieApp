package com.prmto.mova_movieapp.feature_home.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prmto.mova_movieapp.core.data.mapper.toMovie
import com.prmto.mova_movieapp.core.data.models.enums.MoviesApiFunction
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.util.Constants.DEFAULT_REGION
import com.prmto.mova_movieapp.core.util.Constants.STARTING_PAGE
import com.prmto.mova_movieapp.feature_home.data.remote.HomeApi
import kotlinx.coroutines.withTimeout
import timber.log.Timber
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(
    private val homeApi: HomeApi,
    private val language: String,
    private val region: String = DEFAULT_REGION,
    private val apiFunc: MoviesApiFunction,
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val timeOutTimeMilli = 15000L

        val nextPage = params.key ?: STARTING_PAGE
        return try {
            val response = when (apiFunc) {
                MoviesApiFunction.NOW_PLAYING_MOVIES -> {
                    withTimeout(timeOutTimeMilli) {
                        homeApi.getNowPlayingMovies(
                            page = nextPage, language = language, region = region
                        )
                    }
                }
                MoviesApiFunction.POPULAR_MOVIES -> {
                    withTimeout(timeOutTimeMilli) {
                        homeApi.getPopularMovies(
                            page = nextPage, language = language, region = region
                        )
                    }
                }
                MoviesApiFunction.TOP_RATED_MOVIES -> {
                    withTimeout(timeOutTimeMilli) {
                        homeApi.getTopRatedMovies(
                            page = nextPage, language = language, region = region
                        )
                    }
                }
            }
            LoadResult.Page(
                data = response.results.map { it.toMovie() },
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < response.totalPages) response.page.plus(1) else null
            )

        } catch (e: Exception) {
            Timber.d(e)
            LoadResult.Error(throwable = e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }
}

