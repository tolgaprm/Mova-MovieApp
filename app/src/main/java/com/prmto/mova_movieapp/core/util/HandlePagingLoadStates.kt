package com.prmto.mova_movieapp.core.util

import androidx.paging.CombinedLoadStates
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.util.BaseMovieAndTvRecyclerAdapter
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.SearchRecyclerAdapter
import com.prmto.mova_movieapp.feature_home.presentation.home.adapter.NowPlayingRecyclerAdapter
import com.prmto.mova_movieapp.feature_upcoming.presentation.adapter.UpComingBaseAdapter
import okio.IOException

class HandlePagingLoadStates<T : Any>(
    nowPlayingRecyclerAdapter: NowPlayingRecyclerAdapter? = null,
    pagingAdapter: BaseMovieAndTvRecyclerAdapter<T>? = null,
    searchPagingAdapter: SearchRecyclerAdapter? = null,
    upComingPagingAdapter: UpComingBaseAdapter<T>? = null,
    onLoading: () -> Unit,
    onNotLoading: () -> Unit,
    onError: (UiText) -> Unit
) {
    init {
        searchPagingAdapter?.addLoadStateListener { loadStates ->
            handlePagingLoadState(
                loadStates = loadStates,
                onLoading = onLoading,
                onNotLoading = onNotLoading,
                onError = onError
            )
        }

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
        upComingPagingAdapter?.addLoadStateListener { loadStates ->
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
                onError(UiText.StringResource(R.string.oops_something_went_wrong))
            }
        }
    }
}