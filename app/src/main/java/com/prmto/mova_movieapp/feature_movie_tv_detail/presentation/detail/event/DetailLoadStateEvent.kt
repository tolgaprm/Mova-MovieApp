package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.event

import com.prmto.mova_movieapp.core.presentation.util.UiText

sealed class DetailLoadStateEvent {
    object RecommendationLoading : DetailLoadStateEvent()
    object RecommendationNotLoading : DetailLoadStateEvent()
    data class PagingError(val uiText: UiText) : DetailLoadStateEvent()
}
