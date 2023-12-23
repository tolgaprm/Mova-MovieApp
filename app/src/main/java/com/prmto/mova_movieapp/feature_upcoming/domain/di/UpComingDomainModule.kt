package com.prmto.mova_movieapp.feature_upcoming.domain.di

import android.content.Context
import com.prmto.mova_movieapp.feature_upcoming.alarm_manager.AndroidUpComingAlarmScheduler
import com.prmto.mova_movieapp.feature_upcoming.domain.alarmManager.UpComingAlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UpComingDomainModule {

    @Provides
    @ViewModelScoped
    fun bindUpComingAlarmManager(
        @ApplicationContext context: Context
    ): UpComingAlarmScheduler {
        return AndroidUpComingAlarmScheduler(context = context)
    }
}