package com.prmto.mova_movieapp.feature_authentication.presentation.sign_up

import com.prmto.mova_movieapp.core.presentation.util.StandardTextFieldState
import com.prmto.mova_movieapp.feature_authentication.util.AuthError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

data class SignUpUiState(
    val emailState: StandardTextFieldState = StandardTextFieldState(),
    val passwordState: StandardTextFieldState = StandardTextFieldState(),
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