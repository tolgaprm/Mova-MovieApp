package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.event

import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.models.TvSeries

sealed class DetailEvent {
    data class IntentToImdbWebSite(val url: String) : DetailEvent()
    data class IntentToWatchProvidersWebSite(val url: String) : DetailEvent()
    data class ClickToDirectorName(val directorId: Int) : DetailEvent()
    data class ClickActorName(val actorId: Int) : DetailEvent()
    object OnBackPressed : DetailEvent()
    data class SelectedTab(val selectedTabPosition: Int) : DetailEvent()
    object ClickedAddWatchList : DetailEvent()
    object ClickedAddFavoriteList : DetailEvent()
    data class ClickRecommendationItemClick(
        val tvSeries: TvSeries? = null,
        val movie: Movie? = null
    ) : DetailEvent()
}