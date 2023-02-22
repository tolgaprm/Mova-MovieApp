package com.prmto.mova_movieapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prmto.mova_movieapp.core.domain.repository.FirebaseCoreRepository
import com.prmto.mova_movieapp.core.domain.repository.LocalDatabaseRepository
import com.prmto.mova_movieapp.feature_authentication.data.repository.AuthenticationRepositoryImpl
import com.prmto.mova_movieapp.feature_authentication.data.repository.FirebaseMovieRepositoryImpl
import com.prmto.mova_movieapp.feature_authentication.data.repository.FirebaseTvSeriesRepositoryImpl
import com.prmto.mova_movieapp.feature_authentication.domain.repository.AuthenticationRepository
import com.prmto.mova_movieapp.feature_authentication.domain.repository.FirebaseMovieRepository
import com.prmto.mova_movieapp.feature_authentication.domain.repository.FirebaseTvSeriesRepository
import com.prmto.mova_movieapp.feature_authentication.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        firebaseAuth: FirebaseAuth
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideLoginEmailAndPasswordUseCase(
        repository: AuthenticationRepository
    ): SignInWithEmailAndPasswordUseCase {
        return SignInWithEmailAndPasswordUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCreateEmailAndPasswordUseCase(
        repository: AuthenticationRepository
    ): CreateUserWithEmailAndPasswordUseCase {
        return CreateUserWithEmailAndPasswordUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFirebaseMovieRepository(
        firestore: FirebaseFirestore
    ): FirebaseMovieRepository {
        return FirebaseMovieRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideFirebaseTvSeriesRepository(
        firestore: FirebaseFirestore
    ): FirebaseTvSeriesRepository {
        return FirebaseTvSeriesRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideFirebaseUseCases(
        firebaseMovieRepository: FirebaseMovieRepository,
        firebaseTvSeriesRepository: FirebaseTvSeriesRepository,
        firebaseCoreRepository: FirebaseCoreRepository,
        localDatabaseRepository: LocalDatabaseRepository
    ): FirebaseUseCases {
        return FirebaseUseCases(
            getFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase = GetFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase(
                firebaseCoreRepository = firebaseCoreRepository,
                firebaseMovieRepository = firebaseMovieRepository,
                localDatabaseRepository = localDatabaseRepository
            ),
            getMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase = GetMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase(
                firebaseCoreRepository = firebaseCoreRepository,
                firebaseMovieRepository = firebaseMovieRepository,
                localDatabaseRepository = localDatabaseRepository
            ),
            getFavoriteTvSeriesFromFirebaseThenUpdateLocalDatabaseUseCase = GetFavoriteTvSeriesFromFirebaseThenUpdateLocalDatabaseUseCase(
                firebaseCoreRepository = firebaseCoreRepository,
                firebaseTvSeriesRepository = firebaseTvSeriesRepository,
                localDatabaseRepository = localDatabaseRepository
            ),
            getTvSeriesWatchListFromFirebaseThenUpdateLocalDatabaseUseCase = GetTvSeriesWatchListFromFirebaseThenUpdateLocalDatabaseUseCase(
                firebaseCoreRepository = firebaseCoreRepository,
                firebaseTvSeriesRepository = firebaseTvSeriesRepository,
                localDatabaseRepository = localDatabaseRepository
            )
        )
    }
}