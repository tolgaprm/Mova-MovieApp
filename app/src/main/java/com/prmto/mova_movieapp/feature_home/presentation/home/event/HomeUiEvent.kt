package com.prmto.mova_movieapp.feature_home.presentation.home.event

import androidx.navigation.NavDirections
import com.prmto.mova_movieapp.core.presentation.util.UiText

sealed class HomeUiEvent {
    data class NavigateTo(val directions: NavDirections) : HomeUiEvent()
    data class ShowSnackbar(val uiText: UiText) : HomeUiEvent()
}