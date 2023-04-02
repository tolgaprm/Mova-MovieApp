package com.prmto.mova_movieapp.feature_movie_tv_detail.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prmto.mova_movieapp.core.data.mapper.toTvSeries
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.util.Constants
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.DetailApi
import kotlinx.coroutines.withTimeout
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class TvRecPagingSource @Inject constructor(
    private val detailApi: DetailApi,
    private val language: String,
    private val tvId: Int,
) : PagingSource<Int, TvSeries>() {

    override fun getRefreshKey(state: PagingState<Int, TvSeries>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvSeries> {
        val timeOutTimeMilli = 15000L

        val nextPage = params.key ?: Constants.STARTING_PAGE
        return try {
            val response = withTimeout(timeOutTimeMilli) {
                detailApi.getRecommendationsForTv(
                    tvId = tvId,
                    language = language,
                    page = nextPage
                )
            }

            LoadResult.Page(
                data = response.results.map { it.toTvSeries() },
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < response.totalPages) response.page.plus(1) else null
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}

