package com.prmto.mova_movieapp.feature_authentication.domain.repository

import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.util.UiText

interface FirebaseMovieRepository {

    fun getFavoriteMovie(
        userUid: String,
        onSuccess: (List<Movie>) -> Unit,
        onFailure: (uiText: UiText) -> Unit,
    )

    fun getMovieInWatchList(
        userUid: String,
        onSuccess: (List<Movie>) -> Unit,
        onFailure: (uiText: UiText) -> Unit,
    )
}