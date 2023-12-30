package com.prmto.mova_movieapp.core.presentation.util.handlePagingLoadState

import com.prmto.core_domain.util.UiText
import com.prmto.mova_movieapp.feature_home.presentation.home.adapter.NowPlayingRecyclerAdapter

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