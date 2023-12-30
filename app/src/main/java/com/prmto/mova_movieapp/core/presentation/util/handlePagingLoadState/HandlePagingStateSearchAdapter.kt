package com.prmto.mova_movieapp.core.presentation.util.handlePagingLoadState

import com.prmto.core_domain.util.UiText
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.SearchRecyclerAdapter

class HandlePagingStateSearchAdapter(
    searchPagingAdapter: SearchRecyclerAdapter,
    onLoading: () -> Unit,
    onNotLoading: () -> Unit,
    onError: (UiText) -> Unit
) : BasePagingLoadState() {

    init {
        searchPagingAdapter.addLoadStateListener { loadState ->
            handlePagingLoadState(
                loadStates = loadState,
                onLoading = onLoading,
                onNotLoading = onNotLoading,
                onError = onError
            )
        }
    }
}