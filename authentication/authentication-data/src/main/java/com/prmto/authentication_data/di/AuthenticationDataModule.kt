package com.prmto.authentication_data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prmto.authentication_data.repository.AuthenticationRepositoryImpl
import com.prmto.authentication_data.repository.FirebaseMovieRepositoryImpl
import com.prmto.authentication_domain.repository.AuthenticationRepository
import com.prmto.authentication_domain.repository.FirebaseMovieRepository
import com.prmto.authentication_domain.repository.FirebaseTvSeriesRepository
import com.prmto.authentication_domain.use_case.*
import com.prmto.mova_movieapp.feature_authentication.data.repository.FirebaseTvSeriesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AuthenticationDataModule {

    @Provides
    @ViewModelScoped
    fun provideAuthenticationRepository(
        firebaseAuth: FirebaseAuth
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(firebaseAuth)
    }

    @Provides
    @ViewModelScoped
    fun provideLoginEmailAndPasswordUseCase(
        repository: AuthenticationRepository
    ): SignInWithEmailAndPasswordUseCase {
        return SignInWithEmailAndPasswordUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideCreateEmailAndPasswordUseCase(
        repository: AuthenticationRepository
    ): CreateUserWithEmailAndPasswordUseCase {
        return CreateUserWithEmailAndPasswordUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideFirebaseMovieRepository(
        firestore: FirebaseFirestore
    ): FirebaseMovieRepository {
        return FirebaseMovieRepositoryImpl(firestore)
    }

    @Provides
    @ViewModelScoped
    fun provideFirebaseTvSeriesRepository(
        firestore: FirebaseFirestore
    ): FirebaseTvSeriesRepository {
        return FirebaseTvSeriesRepositoryImpl(firestore)
    }
}