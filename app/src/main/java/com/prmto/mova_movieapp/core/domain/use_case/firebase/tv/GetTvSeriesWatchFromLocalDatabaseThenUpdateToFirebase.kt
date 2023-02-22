package com.prmto.mova_movieapp.core.domain.use_case.firebase.tv

import com.prmto.mova_movieapp.core.domain.use_case.database.LocalDatabaseUseCases
import com.prmto.mova_movieapp.core.domain.use_case.firebase.FirebaseCoreUseCases
import com.prmto.mova_movieapp.core.presentation.util.UiText
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase @Inject constructor(
    private val localDatabaseUseCases: LocalDatabaseUseCases,
    private val firebaseCoreUseCases: FirebaseCoreUseCases
) {

    suspend operator fun invoke(
        onSuccess: () -> Unit,
        onFailure: (UiText) -> Unit
    ) {
        val tvSeriesInWatchList =
            localDatabaseUseCases.getTvSeriesInWatchListUseCase().first()

        firebaseCoreUseCases.addTvSeriesToWatchListInFirebaseUseCase(
            tvSeriesInWatchList = tvSeriesInWatchList,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}