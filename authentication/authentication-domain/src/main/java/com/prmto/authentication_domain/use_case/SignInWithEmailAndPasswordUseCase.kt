package com.prmto.authentication_domain.use_case

import com.prmto.authentication_domain.models.AuthenticationResult
import com.prmto.authentication_domain.repository.AuthenticationRepository
import com.prmto.authentication_domain.util.AuthError
import com.prmto.core_domain.util.UiText
import javax.inject.Inject

class SignInWithEmailAndPasswordUseCase @Inject constructor(
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
            return AuthenticationResult(emailError = emailError, passwordError = passwordError)
        }

        return AuthenticationResult(
            result = repository.signInWithEmailAndPassword(
                email = email.trim(),
                password = password,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        )
    }
}