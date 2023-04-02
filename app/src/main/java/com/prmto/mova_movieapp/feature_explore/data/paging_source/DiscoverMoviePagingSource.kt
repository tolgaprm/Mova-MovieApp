package com.prmto.mova_movieapp.feature_explore.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prmto.mova_movieapp.core.data.mapper.toMovie
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.presentation.util.toDiscoveryQueryString
import com.prmto.mova_movieapp.core.presentation.util.toSeparateWithComma
import com.prmto.mova_movieapp.core.util.Constants
import com.prmto.mova_movieapp.feature_explore.data.remote.ExploreApi
import com.prmto.mova_movieapp.feature_explore.presentation.filter_bottom_sheet.state.FilterBottomState
import kotlinx.coroutines.withTimeout
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject


class DiscoverMoviePagingSource @Inject constructor(
    private val exploreApi: ExploreApi,
    private val filterBottomState: FilterBottomState,
    private val language: String
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val timeOutTimeMilli = 15000L

        val nextPage = params.key ?: Constants.STARTING_PAGE
        return try {
            val apiResponse = withTimeout(timeOutTimeMilli) {
                exploreApi.discoverMovie(
                    page = nextPage,
                    language = language,
                    genres = filterBottomState.checkedGenreIdsState.toSeparateWithComma(),
                    sort = filterBottomState.checkedSortState.toDiscoveryQueryString(
                        filterBottomState.categoryState
                    ),
                )
            }

            LoadResult.Page(
                data = apiResponse.results.map { it.toMovie() },
                prevKey = if (nextPage == 1) null else nextPage.minus(1),
                nextKey = if (nextPage < apiResponse.totalPages) nextPage.plus(1) else null
            )

        } catch (e: IOException) {
            LoadResult.Error(throwable = e)
        } catch (e: HttpException) {
            LoadResult.Error(throwable = e)
        }

    }
}