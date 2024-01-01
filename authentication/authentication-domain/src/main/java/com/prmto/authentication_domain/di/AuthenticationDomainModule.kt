package com.prmto.authentication_domain.di

import com.prmto.authentication_domain.repository.FirebaseMovieRepository
import com.prmto.authentication_domain.repository.FirebaseTvSeriesRepository
import com.prmto.authentication_domain.use_case.FirebaseUseCases
import com.prmto.authentication_domain.use_case.GetFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase
import com.prmto.authentication_domain.use_case.GetFavoriteTvSeriesFromFirebaseThenUpdateLocalDatabaseUseCase
import com.prmto.authentication_domain.use_case.GetMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase
import com.prmto.authentication_domain.use_case.GetTvSeriesWatchListFromFirebaseThenUpdateLocalDatabaseUseCase
import com.prmto.core_domain.repository.firebase.FirebaseCoreRepository
import com.prmto.core_domain.repository.local.LocalDatabaseRepository
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