package com.prmto.authentication_domain.repository

import com.google.firebase.auth.AuthCredential
import com.prmto.core_domain.util.SimpleResource

interface AuthenticationRepository {

    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): SimpleResource

    suspend fun createWithEmailAndPassword(
        email: String,
        password: String
    ): SimpleResource

    suspend fun sendPasswordResetEmail(
        email: String
    ): SimpleResource

    suspend fun signInWithCredential(
        credential: AuthCredential
    ): SimpleResource
}