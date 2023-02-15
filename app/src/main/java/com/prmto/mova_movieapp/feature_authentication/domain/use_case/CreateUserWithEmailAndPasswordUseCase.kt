package com.prmto.mova_movieapp.feature_authentication.domain.use_case

import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.feature_authentication.domain.models.AuthenticationResult
import com.prmto.mova_movieapp.feature_authentication.domain.repository.AuthenticationRepository
import com.prmto.mova_movieapp.feature_authentication.util.AuthError
import javax.inject.Inject

class CreateUserWithEmailAndPasswordUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    operator fun invoke(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ): AuthenticationResult {
        val emailError = if (email.isBlank()) AuthError.FieldEmpty else null
        val passwordError = if (password.isBlank()) AuthError.FieldEmpty else null

        if (emailError != null || passwordError != null) {
            return AuthenticationResult(
                emailError = emailError,
                passwordError = passwordError
            )
        }


        return AuthenticationResult(
            result = repository.createWithEmailAndPassword(
                email = email,
                password = password,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        )
    }
}