package com.prmto.mova_movieapp.feature_explore.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prmto.mova_movieapp.core.util.Constants.STARTING_PAGE
import com.prmto.mova_movieapp.feature_explore.data.dto.SearchDto
import com.prmto.mova_movieapp.feature_explore.data.remote.ExploreApi
import kotlinx.coroutines.withTimeout
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class MultiSearchPagingSource @Inject constructor(
    private val exploreApi: ExploreApi,
    private val query: String,
    private val language: String
) : PagingSource<Int, SearchDto>() {
    override fun getRefreshKey(state: PagingState<Int, SearchDto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchDto> {

        val timeOutTimeMilli = 15000L

        val nextPage = params.key ?: STARTING_PAGE

        return try {

            val response = withTimeout(timeOutTimeMilli) {
                exploreApi.multiSearch(
                    query = query,
                    language = language,
                    page = nextPage
                )
            }

            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < response.totalPages) response.page.plus(1) else null
            )
        } catch (e: IOException) {
            Timber.d("Error", e)
            LoadResult.Error(e)
        } catch (e: HttpException) {
            Timber.d("Error", e)
            LoadResult.Error(e)
        }
    }
}