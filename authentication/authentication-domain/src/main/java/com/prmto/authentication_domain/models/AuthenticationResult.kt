package com.prmto.authentication_domain.models

import com.prmto.authentication_domain.util.AuthError
import com.prmto.core_domain.util.SimpleResource

data class AuthenticationResult(
    val emailError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: SimpleResource? = null
)