package com.prmto.authentication_data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.prmto.authentication_domain.repository.FirebaseTvSeriesRepository
import com.prmto.core_data.util.Constants.FIREBASE_FAVORITE_TV_DOCUMENT_NAME
import com.prmto.core_data.util.Constants.FIREBASE_TV_WATCH_DOCUMENT_NAME
import com.prmto.core_data.util.safeCallWithForFirebase
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.util.Resource
import com.prmto.core_domain.util.UiText
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class FirebaseTvSeriesRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) : FirebaseTvSeriesRepository {
    override suspend fun getFavoriteTvSeries(userUid: String): Resource<List<TvSeries>> {
        return safeCallWithForFirebase {
            val result =
                firestore.collection(userUid).document(FIREBASE_FAVORITE_TV_DOCUMENT_NAME).get()
                    .await()
            val tvSeries = documentToListTvSeries(document = result)

            if (tvSeries.isNotEmpty()) {
                Resource.Success(tvSeries)
            } else {
                Resource.Error(UiText.unknownError())
            }
        }
    }

    override suspend fun getTvSeriesInWatchList(userUid: String): Resource<List<TvSeries>> {
        return safeCallWithForFirebase {
            val result =
                firestore.collection(userUid).document(FIREBASE_TV_WATCH_DOCUMENT_NAME).get()
                    .await()
            val tvSeries = documentToListTvSeries(document = result)

            if (tvSeries.isNotEmpty()) {
                Resource.Success(tvSeries)
            } else {
                Resource.Error(UiText.unknownError())
            }
        }
    }

    private fun documentToListTvSeries(
        document: DocumentSnapshot
    ): List<TvSeries> {
        return try {
            val mapOfTvSeries = document.get("tvSeries") as List<*>

            val listOfTvSeries = mapOfTvSeries.map {
                it as Map<*, *>
                val data = it["tvSeries"] as Map<*, *>
                val overview = data["overview"] as String
                val voteAverage = data["voteAverage"] as Double
                val firstAirDate = data["firstAirDate"] as String
                val name = data["name"] as String
                val voteCountByString = data["formattedVoteCount"] as String
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
            listOfTvSeries
        } catch (e: Exception) {
            Timber.e(e.localizedMessage?.toString())
            emptyList()
        }
    }
}