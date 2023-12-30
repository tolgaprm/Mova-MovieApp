package com.prmto.mova_movieapp.core.domain.di

import com.prmto.mova_movieapp.core.domain.repository.firebase.FirebaseCoreManager
import com.prmto.mova_movieapp.core.domain.use_case.firebase.FirebaseCoreUseCases
import com.prmto.mova_movieapp.core.domain.use_case.firebase.GetCurrentUserUseCase
import com.prmto.mova_movieapp.core.domain.use_case.firebase.IsUserSignInUseCase
import com.prmto.mova_movieapp.core.domain.use_case.firebase.SignOutUseCase
import com.prmto.mova_movieapp.core.domain.use_case.firebase.movie.AddMovieToFavoriteListInFirebaseUseCase
import com.prmto.mova_movieapp.core.domain.use_case.firebase.movie.AddMovieToWatchListInFirebaseUseCase
import com.prmto.mova_movieapp.core.domain.use_case.firebase.tv.AddTvSeriesToFavoriteListInFirebaseUseCase
import com.prmto.mova_movieapp.core.domain.use_case.firebase.tv.AddTvSeriesToWatchListInFirebaseUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseCoreDomainModule {

    @Provides
    @Singleton
    fun provideFirebaseCoreUseCases(
        firebaseCoreManager: FirebaseCoreManager
    ): FirebaseCoreUseCases {
        return FirebaseCoreUseCases(
            getCurrentUserUseCase = GetCurrentUserUseCase(firebaseCoreManager.firebaseCoreRepository),
            signOutUseCase = SignOutUseCase(firebaseCoreManager.firebaseCoreRepository),
            isUserSignInUseCase = IsUserSignInUseCase(firebaseCoreManager.firebaseCoreRepository),
            addMovieToFavoriteListInFirebaseUseCase = AddMovieToFavoriteListInFirebaseUseCase(
                firebaseCoreRepository = firebaseCoreManager.firebaseCoreRepository,
                firebaseCoreMovieRepository = firebaseCoreManager.firebaseCoreMovieRepository
            ),
            addMovieToWatchListInFirebaseUseCase = AddMovieToWatchListInFirebaseUseCase(
                firebaseCoreRepository = firebaseCoreManager.firebaseCoreRepository,
                firebaseCoreMovieRepository = firebaseCoreManager.firebaseCoreMovieRepository
            ),
            addTvSeriesToFavoriteListInFirebaseUseCase = AddTvSeriesToFavoriteListInFirebaseUseCase(
                firebaseCoreRepository = firebaseCoreManager.firebaseCoreRepository,
                firebaseCoreTvSeriesRepository = firebaseCoreManager.firebaseCoreTvSeriesRepository
            ),
            addTvSeriesToWatchListInFirebaseUseCase = AddTvSeriesToWatchListInFirebaseUseCase(
                firebaseCoreRepository = firebaseCoreManager.firebaseCoreRepository,
                firebaseCoreTvSeriesRepository = firebaseCoreManager.firebaseCoreTvSeriesRepository
            )
        )
    }
}