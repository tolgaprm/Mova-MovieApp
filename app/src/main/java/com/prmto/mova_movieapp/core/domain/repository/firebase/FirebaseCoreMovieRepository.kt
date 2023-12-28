package com.prmto.mova_movieapp.core.domain.repository.firebase

import com.prmto.mova_movieapp.core.domain.models.movie.Movie
import com.prmto.mova_movieapp.core.presentation.util.UiText

interface FirebaseCoreMovieRepository {
    fun addMovieToFavoriteList(
        userUid: String,
        data: Map<String, List<Movie>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit,
    )

    fun addMovieToWatchList(
        userUid: String,
        data: Map<String, List<Movie>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit,
    )
}