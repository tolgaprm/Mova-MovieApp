package com.prmto.mova_movieapp.feature_explore.presentation.event

import com.prmto.mova_movieapp.core.domain.repository.ConnectivityObserver
import com.prmto.mova_movieapp.feature_explore.presentation.explore.ExploreFragmentDirections

sealed class ExploreFragmentEvent {
    data class MultiSearch(val query: String) : ExploreFragmentEvent()
    object RemoveQuery : ExploreFragmentEvent()
    data class NavigateToDetailBottomSheet(val directions: ExploreFragmentDirections.ActionExploreFragmentToDetailBottomSheet) :
        ExploreFragmentEvent()

    data class UpdateConnectivityStatus(val connectivityStatus: ConnectivityObserver.Status) :
        ExploreFragmentEvent()
}
