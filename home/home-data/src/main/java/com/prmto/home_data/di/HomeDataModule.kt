package com.prmto.home_data.di

import com.prmto.home_data.remote.dataSources.movie.HomeMovieRemoteDataSource
import com.prmto.home_data.remote.dataSources.movie.HomeMovieRemoteDataSourceImpl
import com.prmto.home_data.remote.dataSources.tv.HomeTvRemoteDataSource
import com.prmto.home_data.remote.dataSources.tv.HomeTvRemoteDataSourceImpl
import com.prmto.home_data.repository.movie.HomeMovieRepositoryImpl
import com.prmto.home_data.repository.tv.HomeTvRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeDataModule {
    @Binds
    @ViewModelScoped
    abstract fun bindHomeMovieRemoteDataSource(
        homeMovieRemoteDataSourceImpl: HomeMovieRemoteDataSourceImpl
    ): HomeMovieRemoteDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindHomeTvRemoteDataSource(
        homeTvRemoteDataSourceImpl: HomeTvRemoteDataSourceImpl
    ): HomeTvRemoteDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindHomeMovieRepository(
        homeMovieRepositoryImpl: HomeMovieRepositoryImpl
    ): com.prmto.home_domain.repository.movie.HomeMovieRepository

    @Binds
    @ViewModelScoped
    abstract fun bindHomeTvRepository(
        homeTvRepositoryImpl: HomeTvRepositoryImpl
    ): com.prmto.home_domain.repository.tv.HomeTvRepository
}