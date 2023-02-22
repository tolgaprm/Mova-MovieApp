package com.prmto.mova_movieapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prmto.mova_movieapp.core.data.repository.FirebaseCoreManager
import com.prmto.mova_movieapp.core.data.repository.FirebaseCoreMovieRepositoryImpl
import com.prmto.mova_movieapp.core.data.repository.FirebaseCoreRepositoryImpl
import com.prmto.mova_movieapp.core.data.repository.FirebaseCoreTvSeriesRepositoryImpl
import com.prmto.mova_movieapp.core.domain.repository.FirebaseCoreMovieRepository
import com.prmto.mova_movieapp.core.domain.repository.FirebaseCoreRepository
import com.prmto.mova_movieapp.core.domain.repository.FirebaseCoreTvSeriesRepository
import com.prmto.mova_movieapp.core.domain.use_case.*
import com.prmto.mova_movieapp.core.domain.use_case.firebase.*
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
object FirebaseCoreModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseCoreRepository(
        auth: FirebaseAuth
    ): FirebaseCoreRepository {
        return FirebaseCoreRepositoryImpl(auth)
    }

    @Provides
    @Singleton
    fun provideFirebaseCoreMovieRepository(
        firestore: FirebaseFirestore
    ): FirebaseCoreMovieRepository {
        return FirebaseCoreMovieRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideFirebaseCoreTvSeriesRepository(
        firestore: FirebaseFirestore
    ): FirebaseCoreTvSeriesRepository {
        return FirebaseCoreTvSeriesRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideFirebaseCoreManager(
        firebaseCoreRepository: FirebaseCoreRepository,
        firebaseCoreMovieRepository: FirebaseCoreMovieRepository,
        firebaseCoreTvSeriesRepository: FirebaseCoreTvSeriesRepository
    ): FirebaseCoreManager {
        return FirebaseCoreManager(
            firebaseCoreRepository = firebaseCoreRepository,
            firebaseCoreMovieRepository = firebaseCoreMovieRepository,
            firebaseCoreTvSeriesRepository = firebaseCoreTvSeriesRepository
        )
    }

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