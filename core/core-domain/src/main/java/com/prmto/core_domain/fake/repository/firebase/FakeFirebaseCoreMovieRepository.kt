package com.prmto.core_domain.fake.repository.firebase

import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.repository.firebase.FirebaseCoreMovieRepository
import com.prmto.core_domain.util.Resource
import com.prmto.core_domain.util.SimpleResource
import com.prmto.core_domain.util.UiText

class FakeFirebaseCoreMovieRepository : FirebaseCoreMovieRepository {
    private var isReturnError = false
    override suspend fun addMovieToFavoriteList(
        userUid: String,
        movieInFavoriteList: List<Movie>
    ): SimpleResource {
        return if (isReturnError) {
            Resource.Error(UiText.somethingWentWrong())
        } else {
            Resource.Success(Unit)
        }
    }

    override suspend fun addMovieToWatchList(
        userUid: String,
        moviesInWatchList: List<Movie>
    ): SimpleResource {
        return if (isReturnError) {
            Resource.Error(UiText.somethingWentWrong())
        } else {
            Resource.Success(Unit)
        }
    }
}