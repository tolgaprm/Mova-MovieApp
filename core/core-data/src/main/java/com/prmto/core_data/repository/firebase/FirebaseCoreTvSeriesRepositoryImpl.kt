package com.prmto.core_data.repository.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.prmto.core_data.remote.mapper.tv.toFavoriteTvSeries
import com.prmto.core_data.remote.mapper.tv.toTvSeriesWatchListItem
import com.prmto.core_data.util.Constants
import com.prmto.core_data.util.Constants.FIREBASE_TV_SERIES_FIELD_NAME
import com.prmto.core_data.util.safeCallWithForFirebase
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.repository.firebase.FirebaseCoreTvSeriesRepository
import com.prmto.core_domain.util.Resource
import com.prmto.core_domain.util.SimpleResource
import com.prmto.core_domain.util.UiText
import com.prmto.database.entity.tv.FavoriteTvSeries
import com.prmto.database.entity.tv.TvSeriesWatchListItem
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseCoreTvSeriesRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirebaseCoreTvSeriesRepository {

    override suspend fun addTvSeriesToFavoriteList(
        userUid: String,
        tvSeriesInFavoriteList: List<TvSeries>
    ): SimpleResource {
        return safeCallWithForFirebase {
            val data = mapOf(FIREBASE_TV_SERIES_FIELD_NAME to tvSeriesInFavoriteList)
            val result =
                firestore.collection(userUid).document(Constants.FIREBASE_FAVORITE_TV_DOCUMENT_NAME)
                    .set(data.toConvertToFavoriteTvSeriesMap(), SetOptions.merge())
            result.await()
            if (result.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error(UiText.unknownError())
            }
        }
    }

    override suspend fun addTvSeriesToWatchList(
        userUid: String,
        tvSeriesInWatchList: List<TvSeries>
    ): SimpleResource {
        return safeCallWithForFirebase {
            val data = mapOf(
                FIREBASE_TV_SERIES_FIELD_NAME to tvSeriesInWatchList
            )
            val result =
                firestore.collection(userUid).document(Constants.FIREBASE_TV_WATCH_DOCUMENT_NAME)
                    .set(data.toConvertToTvSeriesWatchMap(), SetOptions.merge())
            result.await()
            if (result.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error(UiText.unknownError())
            }
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