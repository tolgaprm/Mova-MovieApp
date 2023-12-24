package com.prmto.mova_movieapp.core.domain.di

import com.prmto.mova_movieapp.core.data.repository.DataOperationsImpl
import com.prmto.mova_movieapp.core.data.repository.RemoteRepositoryImpl
import com.prmto.mova_movieapp.core.domain.repository.DataStoreOperations
import com.prmto.mova_movieapp.core.domain.repository.RemoteRepository
import com.prmto.mova_movieapp.core.util.DefaultDispatchers
import com.prmto.mova_movieapp.core.util.DispatchersProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreDomainModule {

    @Binds
    @Singleton
    abstract fun bindsRemoteRepository(
        remoteRepositoryImpl: RemoteRepositoryImpl
    ): RemoteRepository

    @Binds
    @Singleton
    abstract fun provideDataStoreOperations(
        dataOperationsImpl: DataOperationsImpl
    ): DataStoreOperations


    @Binds
    @Singleton
    abstract fun provideDispatchers(
        defaultDispatchers: DefaultDispatchers
    ): DispatchersProvider
}