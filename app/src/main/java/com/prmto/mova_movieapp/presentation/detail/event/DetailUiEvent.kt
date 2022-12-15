package com.prmto.mova_movieapp.presentation.detail.event

import com.prmto.mova_movieapp.presentation.util.UiText

sealed class DetailUiEvent {
    data class ShowSnackbar(val uiText: UiText) : DetailUiEvent()
    object NavigateUp : DetailUiEvent()
    data class IntentToImdbWebSite(val url: String) : DetailUiEvent()

}