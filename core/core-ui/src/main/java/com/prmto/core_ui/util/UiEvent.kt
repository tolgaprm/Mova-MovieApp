package com.prmto.core_ui.util

import androidx.navigation.NavDirections
import com.prmto.core_domain.util.UiText

sealed class UiEvent {
    data class NavigateTo(val directions: NavDirections) : UiEvent()
    data class ShowSnackbar(val uiText: UiText) : UiEvent()

    object PopBackStack : UiEvent()
}