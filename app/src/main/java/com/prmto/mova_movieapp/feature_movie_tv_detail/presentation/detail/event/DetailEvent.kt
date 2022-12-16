package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.event

sealed class DetailEvent {
    data class IntentToImdbWebSite(val url: String) : DetailEvent()

    //data class ClickToDirectorName(val directorId: Int) : DetailEvent()
    //data class ClickActorName(val actorId: Int) : DetailEvent()
    data class UpdateMovieId(val movieId: Int) : DetailEvent()
    data class UpdateTvSeriesId(val tvSeriesId: Int) : DetailEvent()
    object OnBackPressed : DetailEvent()
}