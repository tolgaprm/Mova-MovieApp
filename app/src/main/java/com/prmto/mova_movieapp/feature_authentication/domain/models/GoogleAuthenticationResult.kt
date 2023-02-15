package com.prmto.mova_movieapp.feature_authentication.domain.models

import com.prmto.mova_movieapp.core.presentation.util.UiText

data class GoogleAuthenticationResult(
    val errorMessage: UiText? = null,
    val result: Unit? = null
)