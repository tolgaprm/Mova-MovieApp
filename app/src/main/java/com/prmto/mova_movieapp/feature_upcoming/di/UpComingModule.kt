package com.prmto.mova_movieapp.feature_upcoming.di

import com.prmto.mova_movieapp.feature_upcoming.data.remote.UpComingApi
import com.prmto.mova_movieapp.feature_upcoming.data.repository.UpComingRepositoryImpl
import com.prmto.mova_movieapp.feature_upcoming.domain.repository.UpComingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object UpComingModule {

    @Provides
    @ViewModelScoped
    fun provideUpComingApi(retrofit: Retrofit): UpComingApi {
        return retrofit.create(UpComingApi::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideUpComingRepository(
        api: UpComingApi
    ): UpComingRepository {
        return UpComingRepositoryImpl(api = api)
    }

}