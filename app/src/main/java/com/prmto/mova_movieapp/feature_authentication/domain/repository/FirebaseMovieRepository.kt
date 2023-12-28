package com.prmto.mova_movieapp.feature_authentication.domain.repository

import com.prmto.mova_movieapp.core.domain.models.movie.Movie
import com.prmto.mova_movieapp.core.presentation.util.UiText

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