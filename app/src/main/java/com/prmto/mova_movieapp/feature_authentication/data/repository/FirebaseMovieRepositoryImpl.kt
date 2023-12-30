package com.prmto.mova_movieapp.feature_authentication.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.prmto.mova_movieapp.core.data.util.Constants
import com.prmto.mova_movieapp.core.domain.models.movie.Movie
import com.prmto.mova_movieapp.core.domain.util.FirebaseFirestoreErrorMessage.Companion.setExceptionToFirebaseMessage
import com.prmto.mova_movieapp.core.domain.util.UiText
import com.prmto.mova_movieapp.feature_authentication.domain.repository.FirebaseMovieRepository
import timber.log.Timber
import javax.inject.Inject

class FirebaseMovieRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) : FirebaseMovieRepository {

    override fun getFavoriteMovie(
        userUid: String,
        onSuccess: (List<Movie>) -> Unit,
        onFailure: (uiText: UiText) -> Unit,
    ) {
        firestore.collection(userUid).document(Constants.FIREBASE_FAVORITE_MOVIE_DOCUMENT_NAME)
            .get()
            .addOnSuccessListener { document ->
                documentToListMovie(
                    document = document,
                    onSuccess = { movies -> onSuccess(movies) },
                    onFailure = onFailure
                )
            }.addOnFailureListener { exception ->
                setExceptionToFirebaseMessage(exception = exception, onFailure = onFailure)
            }
    }

    override fun getMovieInWatchList(
        userUid: String,
        onSuccess: (List<Movie>) -> Unit,
        onFailure: (uiText: UiText) -> Unit,
    ) {
        firestore.collection(userUid).document(Constants.FIREBASE_MOVIE_WATCH_DOCUMENT_NAME).get()
            .addOnSuccessListener { document ->
                documentToListMovie(
                    document = document,
                    onSuccess = { movies -> onSuccess(movies) },
                    onFailure = onFailure
                )
            }.addOnFailureListener { exception ->
                setExceptionToFirebaseMessage(exception = exception, onFailure = onFailure)
            }
    }

    private fun documentToListMovie(
        document: DocumentSnapshot,
        onSuccess: (List<Movie>) -> Unit,
        onFailure: (uiText: UiText) -> Unit,
    ) {
        try {
            val mapOfMovies = document.get("movies") as List<*>

            val listOfMovie = mapOfMovies.map {
                it as Map<*, *>
                val data = it["movie"] as Map<*, *>
                val overview = data["overview"] as String
                val voteAverage = data["voteAverage"] as Double
                val releaseDate = data["releaseDate"] as String
                val genresBySeparatedByComma =
                    data["genresBySeparatedByComma"] as String
                val voteCountByString = data["voteCountByString"] as String
                val genreByOne = data["genreByOne"] as String
                val id = data["id"] as Number
                val genreIds = data["genreIds"] as List<*>
                val title = data["title"] as String
                val posterPath = data["posterPath"] as String

                val movie = Movie(
                    id = id.toInt(),
                    overview = overview,
                    title = title,
                    posterPath = posterPath,
                    releaseDate = releaseDate,
                    genreIds = genreIds.map { it.toString().toInt() },
                    formattedVoteCount = voteCountByString,
                    genreByOne = genreByOne,
                    genresBySeparatedByComma = genresBySeparatedByComma,
                    voteAverage = voteAverage
                )
                movie
            }
            onSuccess(listOfMovie)
        } catch (e: Exception) {
            Timber.e(e.localizedMessage?.toString())
            onFailure(UiText.unknownError())
        }
    }

}