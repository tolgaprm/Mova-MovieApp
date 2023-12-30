package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.event

import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.SelectableTab

sealed class DetailEvent {
    data class IntentToImdbWebSite(val url: String) : DetailEvent()
    data class ClickToDirectorName(val directorId: Int) : DetailEvent()
    data class ClickActorName(val actorId: Int) : DetailEvent()
    data class SelectedTab(val selectedTab: SelectableTab) : DetailEvent()
    object ClickedAddWatchList : DetailEvent()
    object ClickedAddFavoriteList : DetailEvent()
    data class ClickRecommendationItemClick(
        val tvSeries: TvSeries? = null,
        val movie: Movie? = null
    ) : DetailEvent()
}