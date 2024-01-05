package com.prmto.core_ui.util.handlePagingLoadState

import com.prmto.core_domain.util.UiText
import com.prmto.core_ui.base.BaseMovieAndTvRecyclerAdapter

class HandlePagingLoadStateMovieAndTvBaseRecyclerAdapter<T : Any>(
    pagingAdapter: BaseMovieAndTvRecyclerAdapter<T>,
    onLoading: () -> Unit,
    onNotLoading: () -> Unit,
    onError: (UiText) -> Unit = {}
) : BasePagingLoadState() {

    init {
        pagingAdapter.addLoadStateListener { loadState ->
            handlePagingLoadState(
                loadStates = loadState,
                onLoading = onLoading,
                onNotLoading = onNotLoading,
                onError = onError
            )
        }
    }
}