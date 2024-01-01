package com.prmto.authentication_ui.util

import com.prmto.authentication_domain.util.AuthError

data class StandardTextFieldState(
    val text: String = "",
    val error: AuthError? = null
)
