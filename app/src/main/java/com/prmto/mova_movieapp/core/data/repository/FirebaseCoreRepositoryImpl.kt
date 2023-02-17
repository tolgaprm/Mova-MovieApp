package com.prmto.mova_movieapp.core.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import com.prmto.mova_movieapp.core.domain.repository.FirebaseCoreRepository
import com.prmto.mova_movieapp.core.domain.util.FirebaseFirestoreErrorMessage
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Constants.FIREBASE_FAVORITE_DOCUMENT_NAME
import com.prmto.mova_movieapp.core.util.Constants.FIREBASE_WATCH_DOCUMENT_NAME
import javax.inject.Inject

class FirebaseCoreRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : FirebaseCoreRepository {

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun addMovieToFavoriteList(
        userUid: String,
        data: Map<String, List<Int>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ) {
        firestore.collection(userUid).document(FIREBASE_FAVORITE_DOCUMENT_NAME)
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                setException(exception = exception, onFailure = onFailure)
            }
    }

    override fun addMovieToWatchList(
        userUid: String,
        data: Map<String, List<Int>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit

    ) {
        firestore.collection(userUid).document(FIREBASE_WATCH_DOCUMENT_NAME)
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                setException(exception = exception, onFailure = onFailure)
            }
    }

    override fun addTvSeriesToFavoriteList(
        userUid: String,
        data: Map<String, List<Int>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ) {
        firestore.collection(userUid).document(FIREBASE_FAVORITE_DOCUMENT_NAME)
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                setException(exception = exception, onFailure = onFailure)
            }
    }

    override fun addTvSeriesToWatchList(
        userUid: String,
        data: Map<String, List<Int>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ) {
        firestore.collection(userUid).document(FIREBASE_WATCH_DOCUMENT_NAME)
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                setException(exception = exception, onFailure = onFailure)
            }
    }

    private fun setException(
        exception: Exception,
        onFailure: (uiText: UiText) -> Unit
    ) {
        if (exception is FirebaseFirestoreException) {
            val errorCode = exception.code.toString()
            val errorStringId = FirebaseFirestoreErrorMessage.getMessage(errorCode = errorCode)
            onFailure(UiText.StringResource(errorStringId))
        } else {
            onFailure(UiText.unknownError())
        }
    }


}