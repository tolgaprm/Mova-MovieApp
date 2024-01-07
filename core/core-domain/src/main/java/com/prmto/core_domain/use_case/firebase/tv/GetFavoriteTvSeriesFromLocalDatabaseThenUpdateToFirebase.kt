package com.prmto.core_domain.use_case.firebase.tv

import com.prmto.core_domain.use_case.database.LocalDatabaseUseCases
import com.prmto.core_domain.use_case.firebase.FirebaseCoreUseCases
import com.prmto.core_domain.util.SimpleResource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetFavoriteTvSeriesFromLocalDatabaseThenUpdateToFirebase @Inject constructor(
    private val localDatabaseUseCases: LocalDatabaseUseCases,
    private val firebaseCoreUseCases: FirebaseCoreUseCases
) {

    suspend operator fun invoke(): SimpleResource {
        val favoriteTvSeries = localDatabaseUseCases.getFavoriteTvSeriesUseCase().first()

        return firebaseCoreUseCases.addTvSeriesToFavoriteListInFirebaseUseCase(
            tvSeriesInFavoriteList = favoriteTvSeries
        )
    }
}