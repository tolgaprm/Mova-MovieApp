package com.prmto.mova_movieapp.core.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.prmto.mova_movieapp.core.domain.models.FavoriteMovie
import com.prmto.mova_movieapp.core.domain.models.MovieWatchListItem
import com.prmto.mova_movieapp.core.domain.repository.FirebaseCoreMovieRepository
import com.prmto.mova_movieapp.core.domain.util.FirebaseFirestoreErrorMessage.Companion.setExceptionToFirebaseMessage
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Constants
import javax.inject.Inject

class FirebaseCoreMovieRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirebaseCoreMovieRepository {
    override fun addMovieToFavoriteList(
        userUid: String,
        data: Map<String, List<FavoriteMovie>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ) {
        firestore.collection(userUid).document(Constants.FIREBASE_FAVORITE_MOVIE_DOCUMENT_NAME)
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                setExceptionToFirebaseMessage(exception = exception, onFailure = onFailure)
            }
    }

    override fun addMovieToWatchList(
        userUid: String,
        data: Map<String, List<MovieWatchListItem>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit

    ) {
        firestore.collection(userUid).document(Constants.FIREBASE_MOVIE_WATCH_DOCUMENT_NAME)
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                setExceptionToFirebaseMessage(exception = exception, onFailure = onFailure)
            }
    }
}