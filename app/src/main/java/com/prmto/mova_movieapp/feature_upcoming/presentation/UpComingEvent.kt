package com.prmto.mova_movieapp.feature_upcoming.presentation

import com.prmto.mova_movieapp.feature_upcoming.domain.model.UpcomingRemindEntity

sealed class UpComingEvent {

    object Loading : UpComingEvent()
    data class Error(val message: String) : UpComingEvent()
    object NotLoading : UpComingEvent()

    data class OnClickRemindMe(
        val upcomingRemindEntity: UpcomingRemindEntity,
        val isAddedToRemind: Boolean
    ) : UpComingEvent()
}
