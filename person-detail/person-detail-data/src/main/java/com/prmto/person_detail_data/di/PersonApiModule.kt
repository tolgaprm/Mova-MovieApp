package com.prmto.person_detail_data.di

import com.prmto.person_detail_data.remote.PersonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object PersonApiModule {

    @Provides
    @ViewModelScoped
    fun providePersonApi(retrofit: Retrofit): PersonApi {
        return retrofit.create(PersonApi::class.java)
    }
}