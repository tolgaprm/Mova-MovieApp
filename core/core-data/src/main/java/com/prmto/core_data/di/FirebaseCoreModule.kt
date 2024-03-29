package com.prmto.core_data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prmto.core_data.repository.firebase.FirebaseCoreMovieRepositoryImpl
import com.prmto.core_data.repository.firebase.FirebaseCoreRepositoryImpl
import com.prmto.core_data.repository.firebase.FirebaseCoreTvSeriesRepositoryImpl
import com.prmto.core_domain.repository.firebase.FirebaseCoreManager
import com.prmto.core_domain.repository.firebase.FirebaseCoreMovieRepository
import com.prmto.core_domain.repository.firebase.FirebaseCoreRepository
import com.prmto.core_domain.repository.firebase.FirebaseCoreTvSeriesRepository
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
}