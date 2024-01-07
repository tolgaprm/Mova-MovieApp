package com.prmto.authentication_domain.use_case

import com.prmto.authentication_domain.models.AuthenticationResult
import com.prmto.authentication_domain.repository.AuthenticationRepository
import com.prmto.authentication_domain.util.AuthError
import javax.inject.Inject

class SendPasswordResetEmailUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(
        email: String
    ): AuthenticationResult {

        if (email.isBlank()) {
            return AuthenticationResult(emailError = AuthError.FieldEmpty)
        }

        return AuthenticationResult(
            result = repository.sendPasswordResetEmail(
                email = email.trim()
            )
        )
    }
}