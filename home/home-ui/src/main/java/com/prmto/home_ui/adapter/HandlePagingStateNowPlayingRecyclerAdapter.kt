package com.prmto.home_ui.adapter

import com.prmto.core_domain.util.UiText
import com.prmto.core_ui.util.handlePagingLoadState.BasePagingLoadState

class HandlePagingStateNowPlayingRecyclerAdapter(
    nowPlayingRecyclerAdapter: NowPlayingRecyclerAdapter,
    onLoading: () -> Unit,
    onNotLoading: () -> Unit,
    onError: (UiText) -> Unit = {}
) : BasePagingLoadState() {

    init {
        nowPlayingRecyclerAdapter.addLoadStateListener { loadState ->
            handlePagingLoadState(
                loadStates = loadState,
                onLoading = onLoading,
                onNotLoading = onNotLoading,
                onError = onError
            )
        }
    }
}