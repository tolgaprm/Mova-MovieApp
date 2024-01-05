package com.prmto.authentication_domain.util

sealed class AuthError {
    object FieldEmpty : AuthError()
}
