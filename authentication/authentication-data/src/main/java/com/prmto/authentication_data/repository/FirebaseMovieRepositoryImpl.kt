package com.prmto.authentication_data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.prmto.authentication_domain.repository.FirebaseMovieRepository
import com.prmto.core_data.util.Constants.FIREBASE_FAVORITE_MOVIE_DOCUMENT_NAME
import com.prmto.core_data.util.Constants.FIREBASE_MOVIE_WATCH_DOCUMENT_NAME
import com.prmto.core_data.util.orZero
import com.prmto.core_data.util.safeCallWithForFirebase
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.util.Resource
import com.prmto.core_domain.util.UiText
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class FirebaseMovieRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) : FirebaseMovieRepository {

    override suspend fun getFavoriteMovie(userUid: String): Resource<List<Movie>> {
        return safeCallWithForFirebase {
            val result =
                firestore.collection(userUid).document(FIREBASE_FAVORITE_MOVIE_DOCUMENT_NAME)
                    .get()
                    .await()

            val movies = documentToListMovie(document = result)

            if (movies.isNotEmpty()) {
                Resource.Success(movies)
            } else {
                Resource.Error(UiText.unknownError())
            }
        }
    }

    override suspend fun getMovieInWatchList(userUid: String): Resource<List<Movie>> {
        return safeCallWithForFirebase {
            val result =
                firestore.collection(userUid).document(FIREBASE_MOVIE_WATCH_DOCUMENT_NAME).get()
                    .await()
            val movies = documentToListMovie(document = result)

            if (movies.isNotEmpty()) {
                Resource.Success(movies)
            } else {
                Resource.Error(UiText.unknownError())
            }
        }
    }

    private fun documentToListMovie(
        document: DocumentSnapshot,
    ): List<Movie> {
        try {
            val mapOfMovies = document.get("movies") as List<*>
            val listOfMovie = mapOfMovies.map {
                it as Map<*, *>
                val data = it["movie"] as? Map<*, *>
                val overview = data?.get("overview") as? String
                val voteAverage = data?.get("voteAverage") as? Double
                val releaseDate = data?.get("releaseDate") as? String
                val genresBySeparatedByComma =
                    data?.get("genresBySeparatedByComma") as? String
                val voteCountByString = data?.get("fullReleaseDate") as? String
                val genreByOne = data?.get("genreByOne") as? String
                val id = data?.get("id") as? Number
                val genreIds = data?.get("genreIds") as? List<*>
                val title = data?.get("title") as? String
                val posterPath = data?.get("posterPath") as? String
                val favorite = data?.get("favorite") as? Boolean

                val movie = Movie(
                    id = id?.toInt().orZero(),
                    overview = overview.orEmpty(),
                    title = title.orEmpty(),
                    posterPath = posterPath,
                    releaseDate = releaseDate,
                    genreIds = genreIds?.map { it.toString().toInt() }.orEmpty(),
                    formattedVoteCount = voteCountByString.orEmpty(),
                    genreByOne = genreByOne.orEmpty(),
                    genresBySeparatedByComma = genresBySeparatedByComma.orEmpty(),
                    voteAverage = voteAverage.orZero(),
                    isFavorite = favorite ?: false
                )
                movie
            }
            return listOfMovie
        } catch (e: Exception) {
            Timber.e(e.localizedMessage?.toString())
            return emptyList()
        }
    }
}