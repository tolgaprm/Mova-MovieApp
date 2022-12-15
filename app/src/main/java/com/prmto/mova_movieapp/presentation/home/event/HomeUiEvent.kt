package com.prmto.mova_movieapp.presentation.home.event

import androidx.navigation.NavDirections
import com.prmto.mova_movieapp.presentation.util.UiText

sealed class HomeUiEvent {
    data class NavigateTo(val directions: NavDirections) : HomeUiEvent()
    data class ShowSnackbar(val uiText: UiText) : HomeUiEvent()
    object RetryAllAdapters : HomeUiEvent()
}