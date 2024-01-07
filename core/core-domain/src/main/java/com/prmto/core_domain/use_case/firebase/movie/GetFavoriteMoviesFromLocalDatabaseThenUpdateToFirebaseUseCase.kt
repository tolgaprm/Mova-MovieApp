package com.prmto.core_domain.use_case.firebase.movie

import com.prmto.core_domain.use_case.database.LocalDatabaseUseCases
import com.prmto.core_domain.use_case.firebase.FirebaseCoreUseCases
import com.prmto.core_domain.util.SimpleResource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetFavoriteMoviesFromLocalDatabaseThenUpdateToFirebaseUseCase @Inject constructor(
    private val localDatabaseUseCases: LocalDatabaseUseCases,
    private val firebaseCoreUseCases: FirebaseCoreUseCases
) {
    suspend operator fun invoke(): SimpleResource {
        val moviesInFavoriteList = localDatabaseUseCases.getFavoriteMoviesUseCase().first()
        return firebaseCoreUseCases.addMovieToFavoriteListInFirebaseUseCase(
            moviesInFavoriteList = moviesInFavoriteList
        )
    }
}