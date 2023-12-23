package com.prmto.mova_movieapp.feature_upcoming.data.di

import com.prmto.mova_movieapp.feature_upcoming.data.remote.datasource.UpcomingMovieRemoteDataSource
import com.prmto.mova_movieapp.feature_upcoming.data.remote.datasource.UpcomingMovieRemoteDataSourceImpl
import com.prmto.mova_movieapp.feature_upcoming.data.repository.UpcomingRepositoryImpl
import com.prmto.mova_movieapp.feature_upcoming.domain.repository.UpcomingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UpComingDataModule {

    @Binds
    @ViewModelScoped
    abstract fun bindUpComingMovieRemoteDataSource(
        upComingMovieRemoteDataSourceImpl: UpcomingMovieRemoteDataSourceImpl
    ): UpcomingMovieRemoteDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindUpComingRepository(
        upComingRepositoryImpl: UpcomingRepositoryImpl
    ): UpcomingRepository
}