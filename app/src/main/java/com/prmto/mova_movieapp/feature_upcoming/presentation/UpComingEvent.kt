package com.prmto.mova_movieapp.feature_upcoming.presentation

import com.prmto.mova_movieapp.core.domain.models.Movie

sealed class UpComingEvent {
    data class NavigateToMovieDetailScreen(val movieId: Int) : UpComingEvent()
    data class ClickOnInfoIcon(val movie: Movie) : UpComingEvent()

    object Loading : UpComingEvent()
    data class Error(val message: String) : UpComingEvent()
    object NotLoading : UpComingEvent()
}
