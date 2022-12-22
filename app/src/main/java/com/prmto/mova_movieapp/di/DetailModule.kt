package com.prmto.mova_movieapp.di

import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.DetailApi
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.repository.DetailRepositoryImpl
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.DetailRepository
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.DetailUseCases
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.GetMovieDetailUseCase
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.GetTvDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailModule {

    @Provides
    @Singleton
    fun provideDetailApi(retrofit: Retrofit): DetailApi {
        return retrofit.create(DetailApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDetailRepository(
        detailApi: DetailApi
    ): DetailRepository {
        return DetailRepositoryImpl(detailApi)
    }

    @Provides
    @Singleton
    fun provideDetailUseCases(
        detailRepository: DetailRepository
    ): DetailUseCases {
        return DetailUseCases(
            movieDetailUseCase = GetMovieDetailUseCase(detailRepository),
            tvDetailUseCase = GetTvDetailUseCase(detailRepository)
        )
    }
}