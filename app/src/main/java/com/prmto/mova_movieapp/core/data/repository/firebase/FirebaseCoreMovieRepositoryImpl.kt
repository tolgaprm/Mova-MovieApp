package com.prmto.mova_movieapp.core.data.repository.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.prmto.mova_movieapp.core.data.data_source.local.models.movie.FavoriteMovie
import com.prmto.mova_movieapp.core.data.data_source.local.models.movie.MovieWatchListItem
import com.prmto.mova_movieapp.core.data.mapper.toFavoriteMovie
import com.prmto.mova_movieapp.core.data.mapper.toMovieWatchListItem
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.repository.firebase.FirebaseCoreMovieRepository
import com.prmto.mova_movieapp.core.domain.util.FirebaseFirestoreErrorMessage.Companion.setExceptionToFirebaseMessage
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Constants
import javax.inject.Inject

class FirebaseCoreMovieRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) : FirebaseCoreMovieRepository {
    override fun addMovieToFavoriteList(
        userUid: String,
        data: Map<String, List<Movie>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit,
    ) {
        firestore.collection(userUid).document(Constants.FIREBASE_FAVORITE_MOVIE_DOCUMENT_NAME)
            .set(data.toConvertToFavoriteMovieMap(), SetOptions.merge())
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                setExceptionToFirebaseMessage(exception = exception, onFailure = onFailure)
            }
    }

    override fun addMovieToWatchList(
        userUid: String,
        data: Map<String, List<Movie>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit,

        ) {
        firestore.collection(userUid).document(Constants.FIREBASE_MOVIE_WATCH_DOCUMENT_NAME)
            .set(data.toConvertToMovieWatchMap(), SetOptions.merge())
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                setExceptionToFirebaseMessage(exception = exception, onFailure = onFailure)
            }
    }

    private fun Map<String, List<Movie>>.toConvertToFavoriteMovieMap(): Map<String, List<FavoriteMovie>> {
        return this.mapValues {
            it.value.map { it.toFavoriteMovie() }
        }
    }

    private fun Map<String, List<Movie>>.toConvertToMovieWatchMap(): Map<String, List<MovieWatchListItem>> {
        return this.mapValues {
            it.value.map { it.toMovieWatchListItem() }
        }
    }
}