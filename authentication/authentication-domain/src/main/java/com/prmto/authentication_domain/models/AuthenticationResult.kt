package com.prmto.authentication_domain.models

import com.prmto.authentication_domain.util.AuthError

data class AuthenticationResult(
    val emailError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: Unit? = null
)