package com.prmto.mova_movieapp.di

import com.google.firebase.auth.FirebaseAuth
import com.prmto.mova_movieapp.feature_authentication.data.repository.AuthenticationRepositoryImpl
import com.prmto.mova_movieapp.feature_authentication.domain.repository.AuthenticationRepository
import com.prmto.mova_movieapp.feature_authentication.domain.use_case.CreateUserWithEmailAndPasswordUseCase
import com.prmto.mova_movieapp.feature_authentication.domain.use_case.SignInWithEmailAndPasswordUseCase
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
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

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
}