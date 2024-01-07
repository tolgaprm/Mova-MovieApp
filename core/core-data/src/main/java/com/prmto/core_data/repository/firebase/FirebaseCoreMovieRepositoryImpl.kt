package com.prmto.core_data.repository.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.prmto.core_data.remote.mapper.movie.toFavoriteMovie
import com.prmto.core_data.remote.mapper.movie.toMovieWatchListItem
import com.prmto.core_data.util.Constants
import com.prmto.core_data.util.Constants.FIREBASE_MOVIES_FIELD_NAME
import com.prmto.core_data.util.returnResourceByTaskResult
import com.prmto.core_data.util.safeCallWithForFirebase
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.repository.firebase.FirebaseCoreMovieRepository
import com.prmto.core_domain.util.SimpleResource
import com.prmto.database.entity.movie.FavoriteMovie
import com.prmto.database.entity.movie.MovieWatchListItem
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseCoreMovieRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirebaseCoreMovieRepository {
    override suspend fun addMovieToFavoriteList(
        userUid: String,
        movieInFavoriteList: List<Movie>
    ): SimpleResource {
        return safeCallWithForFirebase {
            val data = mapOf(
                FIREBASE_MOVIES_FIELD_NAME to movieInFavoriteList
            )
            val task =
                firestore.collection(userUid)
                    .document(Constants.FIREBASE_FAVORITE_MOVIE_DOCUMENT_NAME)
                    .set(data.toConvertToFavoriteMovieMap(), SetOptions.merge())
            task.await()
            return task.returnResourceByTaskResult(Unit)
        }
    }

    override suspend fun addMovieToWatchList(
        userUid: String,
        moviesInWatchList: List<Movie>
    ): SimpleResource {
        return safeCallWithForFirebase {
            val data = mapOf(
                FIREBASE_MOVIES_FIELD_NAME to moviesInWatchList
            )
            val result =
                firestore.collection(userUid).document(Constants.FIREBASE_MOVIE_WATCH_DOCUMENT_NAME)
                    .set(data.toConvertToMovieWatchMap(), SetOptions.merge())
            result.await()
            result.returnResourceByTaskResult(Unit)
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