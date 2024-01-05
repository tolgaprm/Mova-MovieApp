package com.prmto.core_ui.detailBottomSheet

import com.prmto.core_domain.util.UiText
import com.prmto.navigation.NavigateFlow

sealed class DetailBottomUiEvent {
    data class NavigateTo(val navigateFlow: NavigateFlow) : DetailBottomUiEvent()
    data class ShowSnackbar(val uiText: UiText) : DetailBottomUiEvent()
    object PopBackStack : DetailBottomUiEvent()
    object ShowAlertDialog : DetailBottomUiEvent()
}
