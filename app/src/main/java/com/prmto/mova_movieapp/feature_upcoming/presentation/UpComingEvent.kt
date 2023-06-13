package com.prmto.mova_movieapp.feature_upcoming.presentation

sealed class UpComingEvent {

    object Loading : UpComingEvent()
    data class Error(val message: String) : UpComingEvent()
    object NotLoading : UpComingEvent()

    data class OnClickRemindMe(
        val movieId: Int,
        val movieTitle: String,
        val isAddedToRemind: Boolean
    ) : UpComingEvent()
}
