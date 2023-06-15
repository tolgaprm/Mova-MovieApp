package com.prmto.mova_movieapp.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.util.NotificationConstants.DEFAULT_NOTIFICATION_CHANNEL_ID
import com.prmto.mova_movieapp.core.util.NotificationConstants.UPCOMING_MOVIE_REMINDER_NOTIFICATION_CHANNEL_ID
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {
    @Provides
    @Singleton
    fun provideNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, "notification_default_channel_id")
            .setSmallIcon(R.drawable.ic_outline_movie_24)
    }

    @Provides
    @DefaultChannelNotificationManagerCompat
    @Singleton
    fun provideDefaultNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager {
        val notificationManager =
            context.getSystemService(NotificationManager::class.java) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                DEFAULT_NOTIFICATION_CHANNEL_ID,
                "Default Notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        return notificationManager
    }

    @Provides
    @UpComingMovieReminderNotificationManagerCompat
    @Singleton
    fun provideUpComingNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager {
        val notificationManager =
            context.getSystemService(NotificationManager::class.java) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                UPCOMING_MOVIE_REMINDER_NOTIFICATION_CHANNEL_ID,
                "Upcoming Movie Reminder",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        return notificationManager
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultChannelNotificationManagerCompat

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UpComingMovieReminderNotificationManagerCompat