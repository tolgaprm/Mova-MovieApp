package com.prmto.mova_movieapp.feature_explore.data.remote.pagingSource.multisearch

import androidx.paging.PagingState
import com.prmto.mova_movieapp.core.data.util.paging.BasePagingSource
import com.prmto.mova_movieapp.feature_explore.data.remote.dto.multisearch.SearchDto

class MultiSearchPagingSource(
    private val fetchMultiSearch: suspend (Int) -> List<SearchDto>
) : BasePagingSource<SearchDto>() {
    override fun getRefreshKey(state: PagingState<Int, SearchDto>): Int? {
        return state.anchorPosition
    }

    override suspend fun fetchData(page: Int): List<SearchDto> {
        return fetchMultiSearch(page)
    }
}