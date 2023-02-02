package com.prmto.mova_movieapp.feature_authentication.presentation.sign_up

import androidx.navigation.NavDirections
import com.prmto.mova_movieapp.core.presentation.util.UiText

sealed class SignUpUiEvent {
    data class NavigateTo(val directions: NavDirections) : SignUpUiEvent()
    data class ShowSnackbar(val uiText: UiText) : SignUpUiEvent()
    object PopBackStack : SignUpUiEvent()
}
