package com.prmto.mova_movieapp.feature_authentication.presentation.forget_password

import com.prmto.mova_movieapp.core.presentation.util.StandardTextFieldState
import com.prmto.mova_movieapp.feature_authentication.util.AuthError

data class ForgetPasswordUiState(
    val emailState: StandardTextFieldState = StandardTextFieldState(),
)

fun ForgetPasswordUiState.updateEmailText(
    text: String
): StandardTextFieldState {
    return this.emailState.copy(
        text = text,
        error = null
    )
}

fun ForgetPasswordUiState.updateEmailError(
    authError: AuthError
): StandardTextFieldState {
    return this.emailState.copy(
        error = authError
    )
}