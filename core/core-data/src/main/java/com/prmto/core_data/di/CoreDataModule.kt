package com.prmto.core_data.di

import com.prmto.core_domain.repository.local.LocalDatabaseRepository
import com.prmto.core_domain.repository.local.MovieLocalRepository
import com.prmto.core_domain.repository.local.TvSeriesLocalRepository
import com.prmto.database.repository.LocalDatabaseRepositoryImpl
import com.prmto.database.repository.MovieLocalRepositoryImpl
import com.prmto.database.repository.TvSeriesLocalRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreDataModule {

    @Binds
    @Singleton
    abstract fun bindMovieLocalRepository(
        movieLocalRepositoryImpl: MovieLocalRepositoryImpl
    ): MovieLocalRepository

    @Binds
    @Singleton
    abstract fun provideTvSeriesLocalRepository(
        tvSeriesLocalRepositoryImpl: TvSeriesLocalRepositoryImpl
    ): TvSeriesLocalRepository

    @Binds
    @Singleton
    abstract fun provideLocalDatabaseRepository(
        localDatabaseRepositoryImpl: LocalDatabaseRepositoryImpl
    ): LocalDatabaseRepository
}