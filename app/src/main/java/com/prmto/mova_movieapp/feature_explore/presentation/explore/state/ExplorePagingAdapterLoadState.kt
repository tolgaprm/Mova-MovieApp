package com.prmto.mova_movieapp.feature_explore.presentation.explore.state

import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.feature_home.presentation.home.state.PagingAdapterLoadStateItem

data class ExplorePagingAdapterLoadState(
    val errorUiText: UiText? = null,
    val filterAdapterState: PagingAdapterLoadStateItem = PagingAdapterLoadStateItem(),
    val searchAdapterState: PagingAdapterLoadStateItem = PagingAdapterLoadStateItem()
)