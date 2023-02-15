package com.prmto.mova_movieapp.di

import com.google.firebase.auth.FirebaseAuth
import com.prmto.mova_movieapp.core.data.repository.FirebaseCoreRepositoryImpl
import com.prmto.mova_movieapp.core.domain.repository.FirebaseCoreRepository
import com.prmto.mova_movieapp.core.domain.use_case.FirebaseCoreUseCases
import com.prmto.mova_movieapp.core.domain.use_case.GetCurrentUserUseCase
import com.prmto.mova_movieapp.core.domain.use_case.IsUserSignInUseCase
import com.prmto.mova_movieapp.core.domain.use_case.SignOutUseCase
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
object FirebaseModule {

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

    @Provides
    @Singleton
    fun provideFirebaseCoreRepository(
        auth: FirebaseAuth
    ): FirebaseCoreRepository {
        return FirebaseCoreRepositoryImpl(auth)
    }

    @Provides
    @Singleton
    fun provideFirebaseCoreUseCases(
        repository: FirebaseCoreRepository
    ): FirebaseCoreUseCases {
        return FirebaseCoreUseCases(
            getCurrentUserUseCase = GetCurrentUserUseCase(repository),
            signOutUseCase = SignOutUseCase(repository),
            isUserSignInUseCase = IsUserSignInUseCase(repository)
        )
    }
}