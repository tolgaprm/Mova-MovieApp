package com.prmto.core_data.di

import com.prmto.core_data.dispatcher.DefaultDispatcher
import com.prmto.core_data.dispatcher.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherModule {

    @Binds
    @Singleton
    abstract fun bindDispatcher(
        defaultDispatcher: DefaultDispatcher
    ): DispatcherProvider
}