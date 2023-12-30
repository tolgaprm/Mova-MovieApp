package com.prmto.mova_movieapp.feature_authentication.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.prmto.core_data.util.Constants.FIREBASE_FAVORITE_TV_DOCUMENT_NAME
import com.prmto.core_data.util.Constants.FIREBASE_TV_WATCH_DOCUMENT_NAME
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.util.FirebaseFirestoreErrorMessage.Companion.setExceptionToFirebaseMessage
import com.prmto.core_domain.util.UiText
import com.prmto.mova_movieapp.feature_authentication.domain.repository.FirebaseTvSeriesRepository
import timber.log.Timber
import javax.inject.Inject

class FirebaseTvSeriesRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) : FirebaseTvSeriesRepository {
    override fun getFavoriteTvSeries(
        userUid: String,
        onSuccess: (List<TvSeries>) -> Unit,
        onFailure: (uiText: UiText) -> Unit,
    ) {
        firestore.collection(userUid).document(FIREBASE_FAVORITE_TV_DOCUMENT_NAME).get()
            .addOnSuccessListener { document ->
                documentToListTvSeries(
                    document = document,
                    onSuccess = { tvSeries -> onSuccess(tvSeries) },
                    onFailure = onFailure
                )
            }.addOnFailureListener { exception ->
                setExceptionToFirebaseMessage(exception = exception, onFailure = onFailure)
            }
    }

    override fun getTvSeriesInWatchList(
        userUid: String,
        onSuccess: (List<TvSeries>) -> Unit,
        onFailure: (uiText: UiText) -> Unit,
    ) {
        firestore.collection(userUid).document(FIREBASE_TV_WATCH_DOCUMENT_NAME).get()
            .addOnSuccessListener { document ->
                documentToListTvSeries(
                    document = document,
                    onSuccess = { tvSeries -> onSuccess(tvSeries) },
                    onFailure = onFailure
                )
            }.addOnFailureListener { exception ->
                setExceptionToFirebaseMessage(exception = exception, onFailure = onFailure)
            }
    }

    private fun documentToListTvSeries(
        document: DocumentSnapshot,
        onSuccess: (List<TvSeries>) -> Unit,
        onFailure: (uiText: UiText) -> Unit,
    ) {
        try {
            val mapOfTvSeries = document.get("tvSeries") as List<*>

            val listOfTvSeries = mapOfTvSeries.map {
                it as Map<*, *>
                val data = it["tvSeries"] as Map<*, *>
                val overview = data["overview"] as String
                val voteAverage = data["voteAverage"] as Double
                val firstAirDate = data["firstAirDate"] as String
                val name = data["name"] as String
                val voteCountByString = data["voteCountByString"] as String
                val genreByOne = data["genreByOne"] as String
                val id = data["id"] as Number
                val genreIds = data["genreIds"] as List<*>
                val posterPath = data["posterPath"] as String

                val movie = TvSeries(
                    id = id.toInt(),
                    overview = overview,
                    name = name,
                    posterPath = posterPath,
                    firstAirDate = firstAirDate,
                    genreIds = genreIds.map { it.toString().toInt() },
                    formattedVoteCount = voteCountByString,
                    genreByOne = genreByOne,
                    voteAverage = voteAverage
                )
                movie
            }
            onSuccess(listOfTvSeries)
        } catch (e: Exception) {
            Timber.e(e.localizedMessage?.toString())
            onFailure(UiText.unknownError())
        }
    }
}