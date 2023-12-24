package com.prmto.mova_movieapp.feature_movie_tv_detail.data.di

import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dataSource.movie.MovieDetailRemoteDataSource
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dataSource.movie.MovieDetailRemoteDataSourceImpl
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dataSource.tv.TvDetailRemoteDataSource
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dataSource.tv.TvDetailRemoteDataSourceImpl
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.repository.movie.MovieDetailRepositoryImpl
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.repository.tv.TvDetailRepositoryImpl
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.movie.MovieDetailRepository
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.tv.TvDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DetailDataModule {
    @Binds
    @ViewModelScoped
    abstract fun bindMovieDetailRemoteDataSource(
        movieDetailRemoteDataSourceImpl: MovieDetailRemoteDataSourceImpl
    ): MovieDetailRemoteDataSource


    @Binds
    @ViewModelScoped
    abstract fun bindTvDetailRemoteDataSource(
        tvDetailRemoteDataSourceImpl: TvDetailRemoteDataSourceImpl
    ): TvDetailRemoteDataSource


    @Binds
    @ViewModelScoped
    abstract fun bindMovieDetailRepository(
        movieRepositoryImpl: MovieDetailRepositoryImpl
    ): MovieDetailRepository

    @Binds
    @ViewModelScoped
    abstract fun bindTvDetailRepository(
        tvRepositoryImpl: TvDetailRepositoryImpl
    ): TvDetailRepository
}