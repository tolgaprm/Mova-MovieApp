package com.prmto.authentication_domain.use_case

import com.prmto.authentication_domain.models.AuthenticationResult
import com.prmto.authentication_domain.repository.AuthenticationRepository
import com.prmto.authentication_domain.util.AuthError
import javax.inject.Inject

class CreateUserWithEmailAndPasswordUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    suspend operator fun invoke(
        email: String,
        password: String
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
                password = password
            )
        )
    }
}