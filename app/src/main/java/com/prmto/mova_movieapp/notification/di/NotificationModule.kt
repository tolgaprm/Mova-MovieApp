package com.prmto.mova_movieapp.notification.di

import android.app.NotificationManager
import android.content.Context
import com.prmto.mova_movieapp.notification.Notifier
import com.prmto.mova_movieapp.notification.variants.UpcomingMovieReminderNotifier
import com.prmto.mova_movieapp.notification.variants.WeekendNotifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager {
        return context.getSystemService(NotificationManager::class.java)
    }

    @Provides
    @UpcomingMovieReminderNotifierQualifier
    fun provideUpcomingMovieReminderNotifier(
        @ApplicationContext context: Context,
        notificationManager: NotificationManager
    ): Notifier {
        return UpcomingMovieReminderNotifier(
            context = context,
            notificationManager = notificationManager
        )
    }

    @Provides
    @WeekendNotifierQualifier
    fun provideWeekendNotifier(
        @ApplicationContext context: Context,
        notificationManager: NotificationManager
    ): Notifier {
        return WeekendNotifier(
            context = context,
            notificationManager = notificationManager
        )
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UpcomingMovieReminderNotifierQualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class WeekendNotifierQualifier