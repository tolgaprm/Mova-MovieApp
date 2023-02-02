package com.prmto.mova_movieapp.feature_authentication.util

sealed class AuthError {
    object FieldEmpty : AuthError()
}
