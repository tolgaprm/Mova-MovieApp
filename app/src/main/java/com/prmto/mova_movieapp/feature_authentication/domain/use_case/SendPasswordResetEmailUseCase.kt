package com.prmto.mova_movieapp.feature_authentication.domain.use_case

import com.prmto.mova_movieapp.core.domain.util.UiText
import com.prmto.mova_movieapp.feature_authentication.domain.models.AuthenticationResult
import com.prmto.mova_movieapp.feature_authentication.domain.repository.AuthenticationRepository
import com.prmto.mova_movieapp.feature_authentication.util.AuthError
import javax.inject.Inject

class SendPasswordResetEmailUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ): AuthenticationResult {

        if (email.isBlank()) {
            return AuthenticationResult(emailError = AuthError.FieldEmpty)
        }

        return AuthenticationResult(
            result = repository.sendPasswordResetEmail(
                email = email.trim(),
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        )
    }
}