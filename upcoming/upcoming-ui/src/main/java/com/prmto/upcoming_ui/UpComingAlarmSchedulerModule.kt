package com.prmto.upcoming_ui

import android.content.Context
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
    ): com.prmto.upcoming_ui.alarmManager.UpComingAlarmScheduler {
        return com.prmto.upcoming_ui.alarmManager.AndroidUpComingAlarmScheduler(
            context = context
        )
    }
}