package com.prmto.mova_movieapp.feature_upcoming.data.remote.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prmto.mova_movieapp.core.util.Constants
import com.prmto.mova_movieapp.feature_upcoming.data.remote.UpComingApi
import com.prmto.mova_movieapp.feature_upcoming.data.remote.mapper.toUpComingMovie
import com.prmto.mova_movieapp.feature_upcoming.domain.model.UpcomingMovie
import javax.inject.Inject

class UpcomingMoviePagingSource @Inject constructor(
    private val api: UpComingApi,
    private val language: String
) : PagingSource<Int, UpcomingMovie>() {
    override fun getRefreshKey(state: PagingState<Int, UpcomingMovie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UpcomingMovie> {
        val nextPage = params.key ?: Constants.STARTING_PAGE

        return try {
            val upComingApiResponse = api.getUpComingMovies(
                page = nextPage,
                language = language
            )

            LoadResult.Page(
                data = upComingApiResponse.results.map { it.toUpComingMovie() },
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < upComingApiResponse.totalPages) nextPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}