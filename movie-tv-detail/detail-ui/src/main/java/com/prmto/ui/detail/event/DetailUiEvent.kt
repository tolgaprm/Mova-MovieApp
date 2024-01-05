package com.prmto.ui.detail.event

import com.prmto.core_domain.util.UiText
import com.prmto.navigation.NavigateFlow

sealed class DetailUiEvent {
    data class ShowSnackbar(val uiText: UiText) : DetailUiEvent()
    object PopBackStack : DetailUiEvent()
    data class IntentToImdbWebSite(val url: String) : DetailUiEvent()
    object ShowAlertDialog : DetailUiEvent()
    data class NavigateTo(val navigateFlow: NavigateFlow) : DetailUiEvent()

    companion object {
        fun showSnackbarErrorMessage(uiText: UiText?) =
            ShowSnackbar(uiText ?: UiText.somethingWentWrong())
    }
}