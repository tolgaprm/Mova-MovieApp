package com.prmto.mova_movieapp.feature_authentication.domain.di

import com.prmto.mova_movieapp.core.domain.repository.firebase.FirebaseCoreRepository
import com.prmto.mova_movieapp.core.domain.repository.local.LocalDatabaseRepository
import com.prmto.mova_movieapp.feature_authentication.domain.repository.FirebaseMovieRepository
import com.prmto.mova_movieapp.feature_authentication.domain.repository.FirebaseTvSeriesRepository
import com.prmto.mova_movieapp.feature_authentication.domain.use_case.FirebaseUseCases
import com.prmto.mova_movieapp.feature_authentication.domain.use_case.GetFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase
import com.prmto.mova_movieapp.feature_authentication.domain.use_case.GetFavoriteTvSeriesFromFirebaseThenUpdateLocalDatabaseUseCase
import com.prmto.mova_movieapp.feature_authentication.domain.use_case.GetMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase
import com.prmto.mova_movieapp.feature_authentication.domain.use_case.GetTvSeriesWatchListFromFirebaseThenUpdateLocalDatabaseUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AuthenticationDomainModule {

    @Provides
    @ViewModelScoped
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