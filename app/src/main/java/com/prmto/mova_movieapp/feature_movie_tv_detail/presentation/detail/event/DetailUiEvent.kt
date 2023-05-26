package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.event

import androidx.navigation.NavDirections
import com.prmto.mova_movieapp.core.presentation.util.UiText

sealed class DetailUiEvent {
    data class ShowSnackbar(val uiText: UiText) : DetailUiEvent()
    object PopBackStack : DetailUiEvent()
    data class IntentToActionView(val url: String) : DetailUiEvent()
    object ShowAlertDialog : DetailUiEvent()
    data class NavigateTo(val directions: NavDirections) : DetailUiEvent()

}