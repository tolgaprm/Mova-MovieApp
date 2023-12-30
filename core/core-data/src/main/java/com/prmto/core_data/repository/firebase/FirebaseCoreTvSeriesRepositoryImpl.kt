package com.prmto.core_data.repository.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.prmto.core_data.remote.mapper.tv.toFavoriteTvSeries
import com.prmto.core_data.remote.mapper.tv.toTvSeriesWatchListItem
import com.prmto.core_data.util.Constants
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.repository.firebase.FirebaseCoreTvSeriesRepository
import com.prmto.core_domain.util.FirebaseFirestoreErrorMessage.Companion.setExceptionToFirebaseMessage
import com.prmto.core_domain.util.UiText
import com.prmto.database.entity.tv.FavoriteTvSeries
import com.prmto.database.entity.tv.TvSeriesWatchListItem
import javax.inject.Inject

class FirebaseCoreTvSeriesRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirebaseCoreTvSeriesRepository {

    override fun addTvSeriesToFavoriteList(
        userUid: String,
        data: Map<String, List<TvSeries>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit,
    ) {
        firestore.collection(userUid).document(Constants.FIREBASE_FAVORITE_TV_DOCUMENT_NAME)
            .set(data.toConvertToFavoriteTvSeriesMap(), SetOptions.merge())
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                setExceptionToFirebaseMessage(exception = exception, onFailure = onFailure)
            }
    }

    override fun addTvSeriesToWatchList(
        userUid: String,
        data: Map<String, List<TvSeries>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit,
    ) {
        firestore.collection(userUid).document(Constants.FIREBASE_TV_WATCH_DOCUMENT_NAME)
            .set(data.toConvertToTvSeriesWatchMap(), SetOptions.merge())
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                setExceptionToFirebaseMessage(exception = exception, onFailure = onFailure)
            }
    }

    private fun Map<String, List<TvSeries>>.toConvertToFavoriteTvSeriesMap(): Map<String, List<FavoriteTvSeries>> {
        return this.mapValues {
            it.value.map { it.toFavoriteTvSeries() }
        }
    }

    private fun Map<String, List<TvSeries>>.toConvertToTvSeriesWatchMap(): Map<String, List<TvSeriesWatchListItem>> {
        return this.mapValues {
            it.value.map { it.toTvSeriesWatchListItem() }
        }
    }
}