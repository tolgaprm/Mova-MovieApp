package com.prmto.mova_movieapp.feature_explore.presentation.explore.event

import com.prmto.mova_movieapp.core.domain.util.UiText

sealed class ExploreAdapterLoadStateEvent {
    data class PagingError(val uiText: UiText) : ExploreAdapterLoadStateEvent()

    object FilterAdapterLoading : ExploreAdapterLoadStateEvent()
    object FilterAdapterNotLoading : ExploreAdapterLoadStateEvent()

    object SearchAdapterLoading : ExploreAdapterLoadStateEvent()
    object SearchAdapterNotLoading : ExploreAdapterLoadStateEvent()
}
