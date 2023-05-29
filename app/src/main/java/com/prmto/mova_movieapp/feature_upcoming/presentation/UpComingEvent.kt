package com.prmto.mova_movieapp.feature_upcoming.presentation

sealed class UpComingEvent {
    data class NavigateToMovieDetailScreen(val movieId: Int) : UpComingEvent()

    object Loading : UpComingEvent()
    data class Error(val message: String) : UpComingEvent()
    object NotLoading : UpComingEvent()
}
