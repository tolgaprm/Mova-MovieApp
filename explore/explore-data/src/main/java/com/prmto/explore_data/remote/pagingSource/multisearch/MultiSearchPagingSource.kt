package com.prmto.explore_data.remote.pagingSource.multisearch

import androidx.paging.PagingState
import com.prmto.core_data.util.paging.BasePagingSource
import com.prmto.explore_data.remote.dto.multisearch.SearchDto
import com.prmto.explore_data.remote.dto.multisearch.multiSearch
import com.prmto.explore_domain.model.MultiSearch

class MultiSearchPagingSource(
    private val fetchMultiSearch: suspend (Int) -> List<SearchDto>
) : BasePagingSource<MultiSearch>() {
    override fun getRefreshKey(state: PagingState<Int, MultiSearch>): Int? {
        return state.anchorPosition
    }

    override suspend fun fetchData(page: Int): List<MultiSearch> {
        return fetchMultiSearch(page).map { it.multiSearch()!! }
    }
}