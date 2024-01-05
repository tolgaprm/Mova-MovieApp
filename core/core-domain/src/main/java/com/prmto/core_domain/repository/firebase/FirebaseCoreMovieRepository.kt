package com.prmto.core_domain.repository.firebase

import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.util.UiText

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