package com.prmto.mova_movieapp.feature_movie_tv_detail.data.di

import com.prmto.mova_movieapp.feature_movie_tv_detail.data.movie.datasources.MovieDetailRemoteDataSource
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.movie.datasources.MovieDetailRemoteDataSourceImpl
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.movie.repository.MovieDetailRepositoryImpl
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.tv.datasources.TvDetailRemoteDataSource
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.tv.datasources.TvDetailRemoteDataSourceImpl
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.tv.repository.TvDetailRepositoryImpl
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.movie.repository.MovieDetailRepository
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.tv.repository.TvDetailRepository
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