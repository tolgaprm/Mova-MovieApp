package com.prmto.mova_movieapp.feature_explore.presentation.adapter

import com.prmto.core_domain.util.UiText
import com.prmto.core_ui.util.handlePagingLoadState.BasePagingLoadState

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