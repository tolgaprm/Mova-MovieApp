package com.prmto.mova_movieapp.core.presentation.util

import androidx.navigation.NavDirections
import com.prmto.mova_movieapp.core.domain.util.UiText

sealed class UiEvent {
    data class NavigateTo(val directions: NavDirections) : UiEvent()
    data class ShowSnackbar(val uiText: UiText) : UiEvent()

    object PopBackStack : UiEvent()
}