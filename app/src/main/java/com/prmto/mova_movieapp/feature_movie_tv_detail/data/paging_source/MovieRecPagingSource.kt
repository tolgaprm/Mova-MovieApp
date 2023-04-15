package com.prmto.mova_movieapp.feature_movie_tv_detail.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prmto.mova_movieapp.core.data.mapper.toMovie
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.util.Constants.STARTING_PAGE
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.DetailApi
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class MovieRecPagingSource @Inject constructor(
    private val detailApi: DetailApi,
    private val language: String,
    private val movieId: Int,
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val timeOutTimeMilli = 15000L

        val nextPage = params.key ?: STARTING_PAGE
        return try {
            val response = withTimeout(timeOutTimeMilli) {
                detailApi.getRecommendationsForMovie(
                    movieId = movieId,
                    language = language,
                    page = nextPage
                )
            }

            LoadResult.Page(
                data = response.results.map { it.toMovie() },
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < response.totalPages) response.page.plus(1) else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}