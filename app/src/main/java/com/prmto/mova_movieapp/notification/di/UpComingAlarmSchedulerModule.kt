package com.prmto.mova_movieapp.notification.di

import android.content.Context
import com.prmto.mova_movieapp.notification.alarmManager.AndroidUpComingAlarmScheduler
import com.prmto.mova_movieapp.notification.alarmManager.UpComingAlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UpComingAlarmSchedulerModule {

    @Provides
    @ViewModelScoped
    fun bindUpComingAlarmManager(
        @ApplicationContext context: Context
    ): UpComingAlarmScheduler {
        return AndroidUpComingAlarmScheduler(
            context = context
        )
    }
}