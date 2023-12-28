package com.prmto.mova_movieapp.feature_authentication.presentation.login

import com.prmto.mova_movieapp.core.presentation.util.StandardTextFieldState
import com.prmto.mova_movieapp.feature_authentication.util.AuthError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

data class LoginUiState(
    val emailState: StandardTextFieldState = StandardTextFieldState(),
    val passwordState: StandardTextFieldState = StandardTextFieldState(),
    val isLoading: Boolean = false
)

fun MutableStateFlow<LoginUiState>.updateEmailText(
    text: String
) {
    this.update {
        it.copy(
            emailState = it.emailState.copy(text = text)
        )
    }
}

fun LoginUiState.emailIsNotBlank(): Boolean {
    return this.emailState.text.isNotBlank()
}

fun MutableStateFlow<LoginUiState>.updateEmailError(
    authError: AuthError
) {
    this.update {
        it.copy(
            emailState = it.emailState.copy(error = authError)
        )
    }
}

fun MutableStateFlow<LoginUiState>.updatePasswordText(
    text: String
) {
    this.update {
        it.copy(
            passwordState = it.passwordState.copy(text = text)
        )
    }
}

fun MutableStateFlow<LoginUiState>.updatePasswordError(
    authError: AuthError
) {
    this.update {
        it.copy(
            passwordState = it.passwordState.copy(error = authError)
        )
    }
}

fun MutableStateFlow<LoginUiState>.updateIsLoading(
    isLoading: Boolean
) {
    this.update {
        it.copy(
            isLoading = isLoading
        )
    }
}