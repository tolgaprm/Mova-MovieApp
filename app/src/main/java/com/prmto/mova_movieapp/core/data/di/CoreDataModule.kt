package com.prmto.mova_movieapp.core.data.di

import com.prmto.mova_movieapp.core.data.repository.local.LocalDatabaseRepositoryImpl
import com.prmto.mova_movieapp.core.data.repository.local.MovieLocalRepositoryImpl
import com.prmto.mova_movieapp.core.data.repository.local.TvSeriesLocalRepositoryImpl
import com.prmto.mova_movieapp.core.domain.repository.local.LocalDatabaseRepository
import com.prmto.mova_movieapp.core.domain.repository.local.MovieLocalRepository
import com.prmto.mova_movieapp.core.domain.repository.local.TvSeriesLocalRepository
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