package com.prmto.mova_movieapp.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prmto.mova_movieapp.data.models.toMovieList
import com.prmto.mova_movieapp.data.remote.TMDBApi
import com.prmto.mova_movieapp.domain.models.Movie
import com.prmto.mova_movieapp.util.Constants.DEFAULT_REGION
import com.prmto.mova_movieapp.util.Constants.STARTING_PAGE
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(
    private val tmdbApi: TMDBApi,
    private val language: String,
    private val region: String= DEFAULT_REGION,
    private val apiFunc: APIFUNC,
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val nextPage = params.key ?: STARTING_PAGE
        return try {

            val response = when (apiFunc) {
                APIFUNC.NOWPLAYINGMOVIES -> {
                    tmdbApi.getNowPlayingMovies(
                        page = nextPage,
                        language = language,
                        region = region
                    )
                }
                APIFUNC.POPULARMOVIES -> {
                    tmdbApi.getPopularMovies(
                        page = nextPage,
                        language = language
                    )
                }
            }

            LoadResult.Page(
                data = response.results.toMovieList(),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < response.totalPages)
                    response.page.plus(1) else null
            )

        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }
}


enum class APIFUNC {
    NOWPLAYINGMOVIES(),
    POPULARMOVIES()
}