package com.prmto.mova_movieapp.feature_movie_tv_detail.data.di

import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.api.DetailApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object DetailApiModule {

    @Provides
    @ViewModelScoped
    fun provideDetailApi(retrofit: Retrofit): DetailApi {
        return retrofit.create(DetailApi::class.java)
    }
}