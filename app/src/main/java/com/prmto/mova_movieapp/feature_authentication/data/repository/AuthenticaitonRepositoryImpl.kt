package com.prmto.mova_movieapp.feature_authentication.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.feature_authentication.domain.repository.AuthenticationRepository
import com.prmto.mova_movieapp.feature_authentication.domain.util.FirebaseAuthMessage
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthenticationRepository {

    override fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                if (exception is FirebaseAuthException) {
                    val errorCode = exception.errorCode
                    val errorStringId = FirebaseAuthMessage.getMessage(errorCode = errorCode)
                    onFailure(UiText.StringResource(errorStringId))
                } else {
                    onFailure(UiText.unknownError())
                }
            }
    }

    override fun createWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(
            email, password
        ).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener { exception ->
            if (exception is FirebaseAuthException) {
                val errorCode = exception.errorCode
                val errorStringId = FirebaseAuthMessage.getMessage(errorCode = errorCode)
                onFailure(UiText.StringResource(errorStringId))
            } else {
                onFailure(UiText.unknownError())
            }
        }
    }
}