package com.prmto.authentication_domain.models

import com.prmto.core_domain.util.SimpleResource
import com.prmto.core_domain.util.UiText

data class GoogleAuthenticationResult(
    val errorMessage: UiText? = null,
    val result: SimpleResource? = null
)