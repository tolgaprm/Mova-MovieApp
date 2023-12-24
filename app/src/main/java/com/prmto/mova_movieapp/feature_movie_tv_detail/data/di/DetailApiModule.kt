package com.prmto.mova_movieapp.feature_movie_tv_detail.data.di

import com.prmto.mova_movieapp.feature_movie_tv_detail.data.api.DetailApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailApiModule {

    @Provides
    @Singleton
    fun provideDetailApi(retrofit: Retrofit): DetailApi {
        return retrofit.create(DetailApi::class.java)
    }
}