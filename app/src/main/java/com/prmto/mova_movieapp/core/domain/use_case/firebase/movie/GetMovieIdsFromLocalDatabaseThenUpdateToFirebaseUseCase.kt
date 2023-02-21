package com.prmto.mova_movieapp.core.domain.use_case.firebase.movie

import com.prmto.mova_movieapp.core.domain.use_case.database.LocalDatabaseUseCases
import com.prmto.mova_movieapp.core.domain.use_case.firebase.FirebaseCoreUseCases
import com.prmto.mova_movieapp.core.presentation.util.UiText
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetFavoriteMovieIdsFromLocalDatabaseThenUpdateToFirebaseUseCase @Inject constructor(
    private val localDatabaseUseCases: LocalDatabaseUseCases,
    private val firebaseCoreUseCases: FirebaseCoreUseCases
) {
    suspend operator fun invoke(
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ) {
        val movieIdsInFavoriteList = localDatabaseUseCases.getFavoriteMovieIdsUseCase().first()
        firebaseCoreUseCases.addMovieToFavoriteListInFirebaseUseCase(
            movieIdsInFavoriteList = movieIdsInFavoriteList,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}