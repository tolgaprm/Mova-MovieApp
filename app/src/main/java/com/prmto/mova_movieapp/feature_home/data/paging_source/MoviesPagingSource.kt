package com.prmto.mova_movieapp.feature_home.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prmto.mova_movieapp.core.data.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.dto.MovieDto
import com.prmto.mova_movieapp.core.data.mapper.toMovie
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.util.Constants.STARTING_PAGE
import timber.log.Timber
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(
    private val getMovies: suspend (Int) -> ApiResponse<MovieDto>
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val nextPage = params.key ?: STARTING_PAGE
        return try {
            val response = getMovies(nextPage)

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

