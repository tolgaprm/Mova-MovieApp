package com.prmto.mova_movieapp.feature_explore.data.di

import com.prmto.mova_movieapp.feature_explore.data.remote.api.ExploreApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object ExploreApiModule {

    @Provides
    @ViewModelScoped
    fun provideExploreApi(retrofit: Retrofit): ExploreApi {
        return retrofit.create(ExploreApi::class.java)
    }
}