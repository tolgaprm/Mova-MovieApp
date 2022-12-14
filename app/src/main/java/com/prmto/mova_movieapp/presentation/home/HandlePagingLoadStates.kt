package com.prmto.mova_movieapp.presentation.home

import androidx.paging.CombinedLoadStates
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.presentation.home.recyler.NowPlayingRecyclerAdapter
import com.prmto.mova_movieapp.presentation.util.BaseMovieAndTvRecyclerAdapter
import com.prmto.mova_movieapp.presentation.util.UiText
import com.prmto.mova_movieapp.util.isErrorWithLoadState
import com.prmto.mova_movieapp.util.isLoading
import okio.IOException

class HandlePagingLoadStates<T : Any>(
    private val nowPlayingRecyclerAdapter: NowPlayingRecyclerAdapter? = null,
    private val pagingAdapter: BaseMovieAndTvRecyclerAdapter<T>? = null,
    onLoading: () -> Unit,
    onNotLoading: () -> Unit,
    onError: (UiText) -> Unit
) {
    init {
        nowPlayingRecyclerAdapter?.addLoadStateListener { loadStates ->
            handlePagingLoadState(
                loadStates = loadStates,
                onLoading = onLoading,
                onNotLoading = onNotLoading,
                onError = onError
            )
        }
        pagingAdapter?.addLoadStateListener { loadStates ->
            handlePagingLoadState(
                loadStates = loadStates,
                onLoading = onLoading,
                onNotLoading = onNotLoading,
                onError = onError
            )
        }
    }

    private fun handlePagingLoadState(
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
                onError(UiText.StringResource(R.string.unknown_error))
            }
        }
    }
}