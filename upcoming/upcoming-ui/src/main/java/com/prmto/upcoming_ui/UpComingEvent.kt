package com.prmto.upcoming_ui

import com.prmto.upcoming_domain.model.UpcomingMovie

sealed class UpComingEvent {

    object Loading : UpComingEvent()
    data class Error(val message: String) : UpComingEvent()
    object NotLoading : UpComingEvent()

    data class OnClickRemindMe(
        val upcomingMovie: UpcomingMovie
    ) : UpComingEvent()
}
