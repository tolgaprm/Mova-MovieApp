package com.prmto.authentication_domain.repository

import com.google.firebase.auth.AuthCredential
import com.prmto.core_domain.util.UiText

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