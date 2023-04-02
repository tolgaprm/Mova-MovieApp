package com.prmto.mova_movieapp.core.data.repository.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.prmto.mova_movieapp.core.data.data_source.local.models.tv.FavoriteTvSeries
import com.prmto.mova_movieapp.core.data.data_source.local.models.tv.TvSeriesWatchListItem
import com.prmto.mova_movieapp.core.data.mapper.toFavoriteTvSeries
import com.prmto.mova_movieapp.core.data.mapper.toTvSeriesWatchListItem
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.domain.repository.firebase.FirebaseCoreTvSeriesRepository
import com.prmto.mova_movieapp.core.domain.util.FirebaseFirestoreErrorMessage.Companion.setExceptionToFirebaseMessage
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Constants
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