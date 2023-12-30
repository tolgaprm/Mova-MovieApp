package com.prmto.mova_movieapp.core.util.handlePagingLoadState

import com.prmto.mova_movieapp.core.domain.util.UiText
import com.prmto.mova_movieapp.core.presentation.base.BaseMovieAndTvRecyclerAdapter

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