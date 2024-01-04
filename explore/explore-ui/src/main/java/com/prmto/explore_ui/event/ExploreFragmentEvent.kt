package com.prmto.explore_ui.event

sealed class ExploreFragmentEvent {
    data class MultiSearch(val query: String) : ExploreFragmentEvent()
    object RemoveQuery : ExploreFragmentEvent()
}
