package com.prmto.mova_movieapp.feature_upcoming.presentation.adapter

import com.prmto.core_domain.util.UiText
import com.prmto.core_ui.util.handlePagingLoadState.BasePagingLoadState

class HandlePagingStateUpComingPagingAdapter(
    upComingPagingAdapter: UpComingMovieAdapter,
    onLoading: () -> Unit,
    onNotLoading: () -> Unit,
    onError: (UiText) -> Unit
) : BasePagingLoadState() {
    init {
        upComingPagingAdapter.addLoadStateListener { loadState ->
            handlePagingLoadState(
                loadStates = loadState,
                onLoading = onLoading,
                onNotLoading = onNotLoading,
                onError = onError
            )
        }
    }
}