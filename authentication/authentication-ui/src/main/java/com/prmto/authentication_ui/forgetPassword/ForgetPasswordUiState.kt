package com.prmto.authentication_ui.forgetPassword

import com.prmto.authentication_domain.util.AuthError
import com.prmto.authentication_ui.util.StandardTextFieldState

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