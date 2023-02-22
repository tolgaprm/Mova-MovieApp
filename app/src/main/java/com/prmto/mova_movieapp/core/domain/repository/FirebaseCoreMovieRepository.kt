package com.prmto.mova_movieapp.core.domain.repository

import com.prmto.mova_movieapp.core.domain.models.FavoriteMovie
import com.prmto.mova_movieapp.core.domain.models.MovieWatchListItem
import com.prmto.mova_movieapp.core.presentation.util.UiText

interface FirebaseCoreMovieRepository {
    fun addMovieToFavoriteList(
        userUid: String,
        data: Map<String, List<FavoriteMovie>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )

    fun addMovieToWatchList(
        userUid: String,
        data: Map<String, List<MovieWatchListItem>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )
}