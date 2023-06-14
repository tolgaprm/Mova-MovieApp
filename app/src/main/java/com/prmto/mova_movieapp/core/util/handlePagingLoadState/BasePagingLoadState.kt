package com.prmto.mova_movieapp.core.util.handlePagingLoadState

import androidx.paging.CombinedLoadStates
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.isErrorWithLoadState
import com.prmto.mova_movieapp.core.util.isLoading
import okio.IOException

open class BasePagingLoadState(

) {

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

