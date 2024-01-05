package com.prmto.core_ui.util

import androidx.navigation.NavDirections
import com.prmto.core_domain.util.UiText
import com.prmto.navigation.NavigateFlow

sealed class UiEvent {
    data class NavigateToFlow(val navigateFlow: NavigateFlow) : UiEvent()

    data class NavigateToDirections(val directions: NavDirections) : UiEvent()

    data class ShowSnackbar(val uiText: UiText) : UiEvent()

    object PopBackStack : UiEvent()
}