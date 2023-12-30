package com.prmto.core_data.repository.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.prmto.core_data.remote.mapper.movie.toFavoriteMovie
import com.prmto.core_data.remote.mapper.movie.toMovieWatchListItem
import com.prmto.core_data.util.Constants
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.repository.firebase.FirebaseCoreMovieRepository
import com.prmto.core_domain.util.FirebaseFirestoreErrorMessage.Companion.setExceptionToFirebaseMessage
import com.prmto.core_domain.util.UiText
import com.prmto.database.entity.movie.FavoriteMovie
import com.prmto.database.entity.movie.MovieWatchListItem
import javax.inject.Inject

class FirebaseCoreMovieRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
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