package com.prmto.mova_movieapp.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prmto.mova_movieapp.data.models.toTvSeries
import com.prmto.mova_movieapp.data.remote.TMDBApi
import com.prmto.mova_movieapp.domain.models.TvSeries
import com.prmto.mova_movieapp.util.Constants.STARTING_PAGE
import javax.inject.Inject

class TvPagingSource @Inject constructor(
    private val tmdb: TMDBApi,
    private val language: String
) : PagingSource<Int, TvSeries>() {
    override fun getRefreshKey(state: PagingState<Int, TvSeries>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvSeries> {

        val nextPage = params.key ?: STARTING_PAGE

        return try {
            val response = tmdb.getPopularTvs(
                page = nextPage,
                language = language
            )

            LoadResult.Page(
                data = response.results.toTvSeries(),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < response.totalPages)
                    response.page.plus(1) else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}