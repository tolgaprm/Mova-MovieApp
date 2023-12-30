package com.prmto.mova_movieapp.feature_upcoming.data.di

import com.prmto.mova_movieapp.database.MovaDatabase
import com.prmto.mova_movieapp.feature_upcoming.data.local.dao.UpComingDao
import com.prmto.mova_movieapp.feature_upcoming.data.remote.api.UpcomingApi
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

    @Provides
    @ViewModelScoped
    fun upComingDao(
        movaDatabase: MovaDatabase
    ): UpComingDao {
        return movaDatabase.upcomingDao
    }
}