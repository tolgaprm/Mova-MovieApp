package com.prmto.authentication_ui.signUp

import com.prmto.authentication_domain.util.AuthError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

data class SignUpUiState(
    val emailState: com.prmto.authentication_ui.util.StandardTextFieldState = com.prmto.authentication_ui.util.StandardTextFieldState(),
    val passwordState: com.prmto.authentication_ui.util.StandardTextFieldState = com.prmto.authentication_ui.util.StandardTextFieldState(),
    val isLoading: Boolean = false
)

fun MutableStateFlow<SignUpUiState>.updateEmailText(
    text: String
) {
    this.update {
        it.copy(
            emailState = it.emailState.copy(text = text)
        )
    }
}

fun SignUpUiState.emailIsNotBlank(): Boolean {
    return this.emailState.text.isNotBlank()
}

fun MutableStateFlow<SignUpUiState>.updateEmailError(
    authError: AuthError
) {
    this.update {
        it.copy(
            emailState = it.emailState.copy(error = authError)
        )
    }
}

fun MutableStateFlow<SignUpUiState>.updatePasswordText(
    text: String
) {
    this.update {
        it.copy(
            passwordState = it.passwordState.copy(text = text)
        )
    }
}

fun MutableStateFlow<SignUpUiState>.updatePasswordError(
    authError: AuthError
) {
    this.update {
        it.copy(
            passwordState = it.passwordState.copy(error = authError)
        )
    }
}

fun MutableStateFlow<SignUpUiState>.updateIsLoading(
    isLoading: Boolean
) {
    this.update {
        it.copy(
            isLoading = isLoading
        )
    }
}