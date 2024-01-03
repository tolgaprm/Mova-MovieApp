package com.prmto.ui.detail.event

import androidx.navigation.NavDirections
import com.prmto.core_domain.util.UiText

sealed class DetailUiEvent {
    data class ShowSnackbar(val uiText: UiText) : DetailUiEvent()
    object PopBackStack : DetailUiEvent()
    data class IntentToImdbWebSite(val url: String) : DetailUiEvent()
    object ShowAlertDialog : DetailUiEvent()
    data class NavigateTo(val directions: NavDirections) : DetailUiEvent()

    companion object {
        fun showSnackbarErrorMessage(uiText: UiText?) =
            ShowSnackbar(uiText ?: UiText.somethingWentWrong())
    }
}