package com.prmto.mova_movieapp.feature_home.data.di

import com.prmto.mova_movieapp.feature_home.data.movie.datasource.HomeMovieRemoteDataSource
import com.prmto.mova_movieapp.feature_home.data.movie.datasource.HomeMovieRemoteDataSourceImpl
import com.prmto.mova_movieapp.feature_home.data.movie.repository.HomeMovieRepositoryImpl
import com.prmto.mova_movieapp.feature_home.data.tv.datasource.HomeTvRemoteDataSource
import com.prmto.mova_movieapp.feature_home.data.tv.datasource.HomeTvRemoteDataSourceImpl
import com.prmto.mova_movieapp.feature_home.data.tv.repository.HomeTvRepositoryImpl
import com.prmto.mova_movieapp.feature_home.domain.movie.HomeMovieRepository
import com.prmto.mova_movieapp.feature_home.domain.tv.HomeTvRepository
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