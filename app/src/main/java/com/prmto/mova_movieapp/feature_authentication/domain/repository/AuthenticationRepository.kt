package com.prmto.mova_movieapp.feature_authentication.domain.repository

import com.google.firebase.auth.AuthCredential
import com.prmto.mova_movieapp.core.presentation.util.UiText

interface AuthenticationRepository {

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )

    fun createWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )

    fun sendPasswordResetEmail(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )

    fun signInWithCredential(
        credential: AuthCredential,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )

}