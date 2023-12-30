package com.prmto.upcoming_data.di

import com.prmto.upcoming_data.remote.api.UpcomingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object UpcomingApiModule {

    @Provides
    @ViewModelScoped
    fun provideUpComingApi(retrofit: Retrofit): UpcomingApi {
        return retrofit.create(UpcomingApi::class.java)
    }
}