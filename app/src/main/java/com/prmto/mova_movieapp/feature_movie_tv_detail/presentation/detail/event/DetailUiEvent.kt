package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.event

import com.prmto.mova_movieapp.core.presentation.util.UiText

sealed class DetailUiEvent {
    data class ShowSnackbar(val uiText: UiText) : DetailUiEvent()
    object PopBackStack : DetailUiEvent()
    data class IntentToImdbWebSite(val url: String) : DetailUiEvent()

}