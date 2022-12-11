package com.prmto.mova_movieapp.presentation.util

import androidx.navigation.NavDirections

sealed class UiEvent {
    data class NavigateTo(val directions: NavDirections) : UiEvent()
}
