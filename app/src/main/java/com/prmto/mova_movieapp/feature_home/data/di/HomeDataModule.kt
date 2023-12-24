package com.prmto.mova_movieapp.feature_home.data.di

import com.prmto.mova_movieapp.feature_home.data.remote.dataSources.movie.HomeMovieRemoteDataSource
import com.prmto.mova_movieapp.feature_home.data.remote.dataSources.movie.HomeMovieRemoteDataSourceImpl
import com.prmto.mova_movieapp.feature_home.data.remote.dataSources.tv.HomeTvRemoteDataSource
import com.prmto.mova_movieapp.feature_home.data.remote.dataSources.tv.HomeTvRemoteDataSourceImpl
import com.prmto.mova_movieapp.feature_home.data.repository.movie.HomeMovieRepositoryImpl
import com.prmto.mova_movieapp.feature_home.data.repository.tv.HomeTvRepositoryImpl
import com.prmto.mova_movieapp.feature_home.domain.repository.movie.HomeMovieRepository
import com.prmto.mova_movieapp.feature_home.domain.repository.tv.HomeTvRepository
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
    ): HomeMovieRepository

    @Binds
    @ViewModelScoped
    abstract fun bindHomeTvRepository(
        homeTvRepositoryImpl: HomeTvRepositoryImpl
    ): HomeTvRepository
}