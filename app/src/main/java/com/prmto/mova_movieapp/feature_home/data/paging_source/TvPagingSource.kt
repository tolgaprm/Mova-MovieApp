package com.prmto.mova_movieapp.feature_home.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prmto.mova_movieapp.core.data.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.dto.TvSeriesDto
import com.prmto.mova_movieapp.core.data.mapper.toTvSeries
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.util.Constants.STARTING_PAGE
import javax.inject.Inject

class TvPagingSource @Inject constructor(
    private val getTvSeries: suspend (Int) -> ApiResponse<TvSeriesDto>
) : PagingSource<Int, TvSeries>() {
    override fun getRefreshKey(state: PagingState<Int, TvSeries>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvSeries> {

        val nextPage = params.key ?: STARTING_PAGE

        return try {

            val response = getTvSeries(nextPage)


            LoadResult.Page(
                data = response.results.map { it.toTvSeries() },
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < response.totalPages)
                    response.page.plus(1) else null
            )

        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}