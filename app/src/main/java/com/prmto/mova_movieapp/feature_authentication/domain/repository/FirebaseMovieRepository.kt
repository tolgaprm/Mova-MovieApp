package com.prmto.mova_movieapp.feature_authentication.domain.repository

import com.prmto.mova_movieapp.core.domain.models.FavoriteMovie
import com.prmto.mova_movieapp.core.domain.models.MovieWatchListItem
import com.prmto.mova_movieapp.core.presentation.util.UiText

interface FirebaseMovieRepository {

    fun getFavoriteMovie(
        userUid: String,
        onSuccess: (List<FavoriteMovie>) -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )

    fun getMovieInWatchList(
        userUid: String,
        onSuccess: (List<MovieWatchListItem>) -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )
}