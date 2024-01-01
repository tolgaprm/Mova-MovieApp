package com.prmto.core_ui.detailBottomSheet

import androidx.navigation.NavDirections
import com.prmto.core_domain.util.UiText

sealed class DetailBottomUiEvent {
    data class NavigateTo(val directions: NavDirections) : DetailBottomUiEvent()
    data class ShowSnackbar(val uiText: UiText) : DetailBottomUiEvent()
    object PopBackStack : DetailBottomUiEvent()
    object ShowAlertDialog : DetailBottomUiEvent()
}
