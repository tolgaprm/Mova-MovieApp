package com.prmto.mova_movieapp.feature_explore.data.di

import com.prmto.mova_movieapp.feature_explore.data.remote.ExploreApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideExploreApi(retrofit: Retrofit): ExploreApi {
        return retrofit.create(ExploreApi::class.java)
    }
}