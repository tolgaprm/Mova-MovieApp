package com.prmto.core_ui.util.handlePagingLoadState

import androidx.paging.CombinedLoadStates
import com.prmto.core_domain.util.UiText
import com.prmto.core_ui.R
import com.prmto.core_ui.util.isErrorWithLoadState
import com.prmto.core_ui.util.isLoading
import okio.IOException

open class BasePagingLoadState {

    protected fun handlePagingLoadState(
        loadStates: CombinedLoadStates,
        onLoading: () -> Unit,
        onNotLoading: () -> Unit,
        onError: (UiText) -> Unit
    ) {
        if (loadStates.isLoading()) {
            onLoading()
        } else {
            onNotLoading()
        }
        loadStates.isErrorWithLoadState()?.let { loadStateError ->
            if (loadStateError.error is IOException) {
                onError(UiText.StringResource(R.string.internet_error))
            } else {
                onError(UiText.StringResource(R.string.oops_something_went_wrong))
            }
        }
    }
}

