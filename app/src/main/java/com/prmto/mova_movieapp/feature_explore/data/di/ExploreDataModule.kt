package com.prmto.mova_movieapp.feature_explore.data.di

import com.prmto.mova_movieapp.feature_explore.data.remote.dataSources.movie.ExploreMovieRemoteDataSource
import com.prmto.mova_movieapp.feature_explore.data.remote.dataSources.movie.ExploreMovieRemoteDataSourceImpl
import com.prmto.mova_movieapp.feature_explore.data.remote.dataSources.multisearch.ExploreMultiSearchRemoteDataSource
import com.prmto.mova_movieapp.feature_explore.data.remote.dataSources.multisearch.ExploreMultiSearchRemoteDataSourceImpl
import com.prmto.mova_movieapp.feature_explore.data.remote.dataSources.tv.ExploreTvRemoteDataSource
import com.prmto.mova_movieapp.feature_explore.data.remote.dataSources.tv.ExploreTvRemoteDataSourceImpl
import com.prmto.mova_movieapp.feature_explore.data.repository.ExploreRepositoryImpl
import com.prmto.mova_movieapp.feature_explore.domain.repository.ExploreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ExploreDataModule {

    @Binds
    @ViewModelScoped
    abstract fun bindExploreMovieRemoteDataSource(
        exploreMovieRemoteDataSourceImpl: ExploreMovieRemoteDataSourceImpl
    ): ExploreMovieRemoteDataSource


    @Binds
    @ViewModelScoped
    abstract fun bindExploreTvRemoteDataSource(
        exploreTvRemoteDataSourceImpl: ExploreTvRemoteDataSourceImpl
    ): ExploreTvRemoteDataSource


    @Binds
    @ViewModelScoped
    abstract fun bindMultiSearchRemoteDataSourceImpl(
        exploreMultiSearchRemoteDataSourceImpl: ExploreMultiSearchRemoteDataSourceImpl
    ): ExploreMultiSearchRemoteDataSource


    @Binds
    @ViewModelScoped
    abstract fun bindExploreRepository(
        exploreRepositoryImpl: ExploreRepositoryImpl
    ): ExploreRepository
}