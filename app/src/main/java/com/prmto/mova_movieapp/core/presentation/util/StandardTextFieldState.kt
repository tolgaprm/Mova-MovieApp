package com.prmto.mova_movieapp.core.presentation.util

import com.prmto.mova_movieapp.feature_authentication.util.AuthError

data class StandardTextFieldState(
    val text: String = "",
    val error: AuthError? = null
)
