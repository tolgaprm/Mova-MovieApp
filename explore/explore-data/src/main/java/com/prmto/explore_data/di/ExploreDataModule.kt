package com.prmto.explore_data.di

import com.prmto.explore_data.remote.dataSources.movie.ExploreMovieRemoteDataSource
import com.prmto.explore_data.remote.dataSources.movie.ExploreMovieRemoteDataSourceImpl
import com.prmto.explore_data.remote.dataSources.multisearch.ExploreMultiSearchRemoteDataSource
import com.prmto.explore_data.remote.dataSources.multisearch.ExploreMultiSearchRemoteDataSourceImpl
import com.prmto.explore_data.remote.dataSources.tv.ExploreTvRemoteDataSource
import com.prmto.explore_data.remote.dataSources.tv.ExploreTvRemoteDataSourceImpl
import com.prmto.explore_data.repository.ExploreRepositoryImpl
import com.prmto.explore_domain.repository.ExploreRepository
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