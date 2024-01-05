package com.prmto.core_data.di

import com.prmto.core_data.repository.GenreRepositoryImpl
import com.prmto.core_domain.repository.GenreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindsRemoteRepository(
        remoteRepositoryImpl: GenreRepositoryImpl
    ): GenreRepository
}