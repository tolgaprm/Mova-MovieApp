package com.prmto.mova_movieapp.core.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.prmto.mova_movieapp.core.presentation.util.UiText

interface FirebaseCoreRepository {

    fun getCurrentUser(): FirebaseUser?

    fun signOut()

    fun addMovieToFavoriteList(
        userUid: String,
        data: Map<String, List<Int>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )

    fun addMovieToWatchList(
        userUid: String,
        data: Map<String, List<Int>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )

    fun addTvSeriesToFavoriteList(
        userUid: String,
        data: Map<String, List<Int>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit

    )

    fun addTvSeriesToWatchList(
        userUid: String,
        data: Map<String, List<Int>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )


}