package com.prmto.mova_movieapp.feature_explore.data.multisearch

import androidx.paging.PagingState
import com.prmto.mova_movieapp.core.data.paging.BasePagingSource
import com.prmto.mova_movieapp.feature_explore.data.multisearch.dto.SearchDto

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