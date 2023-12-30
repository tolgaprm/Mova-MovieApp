package com.prmto.mova_movieapp.core.domain.use_case.firebase.movie

import com.prmto.mova_movieapp.core.domain.use_case.database.LocalDatabaseUseCases
import com.prmto.mova_movieapp.core.domain.use_case.firebase.FirebaseCoreUseCases
import com.prmto.mova_movieapp.core.domain.util.UiText
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetMovieWatchListFromLocalDatabaseThenUpdateToFirebase @Inject constructor(
    private val localDatabaseUseCases: LocalDatabaseUseCases,
    private val firebaseCoreUseCases: FirebaseCoreUseCases
) {

    suspend operator fun invoke(
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ) {
        val moviesInWatchList = localDatabaseUseCases.getMoviesInWatchListUseCase().first()

        firebaseCoreUseCases.addMovieToWatchListInFirebaseUseCase(
            moviesInWatchList = moviesInWatchList,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}