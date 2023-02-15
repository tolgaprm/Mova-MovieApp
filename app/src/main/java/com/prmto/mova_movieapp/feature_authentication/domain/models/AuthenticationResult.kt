package com.prmto.mova_movieapp.feature_authentication.domain.models

import com.prmto.mova_movieapp.feature_authentication.util.AuthError

data class AuthenticationResult(
    val emailError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: Unit? = null
)