package com.prmto.mova_movieapp.feature_upcoming.alarm_manager

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.prmto.mova_movieapp.MainActivity
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.util.NotificationConstants
import com.prmto.mova_movieapp.di.UpComingMovieReminderNotificationManagerCompat
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class UpComingAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder

    @Inject
    @UpComingMovieReminderNotificationManagerCompat
    lateinit var notificationManager: NotificationManager
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            intent?.let {
                val movieId = intent.getIntExtra(AndroidUpComingAlarmScheduler.EXTRA_MOVIE_ID, -1)
                val movieTitle =
                    intent.getStringExtra(AndroidUpComingAlarmScheduler.EXTRA_MOVIE_TITLE)

                if (movieId == -1) return

                showNotification(
                    appContext = context,
                    movieId = movieId,
                    movieTitle = movieTitle ?: ""
                )
            }
        } ?: return
    }

    private fun showNotification(
        appContext: Context,
        movieId: Int,
        movieTitle: String,
    ) {
        val intent = Intent(appContext, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            appContext, movieId, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification =
            notificationBuilder.setChannelId(NotificationConstants.UPCOMING_MOVIE_REMINDER_NOTIFICATION_CHANNEL_ID)
                .setContentTitle(appContext.getString(R.string.upcoming_movie_release_title))
                .setContentText(
                    appContext.getString(
                        R.string.upcoming_movie_release_basic,
                        movieTitle
                    )
                )
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(
                            appContext.getString(
                                R.string.upcoming_movie_set_big_context,
                                movieTitle
                            )
                        )
                )
                .setSmallIcon(R.drawable.ic_outline_movie_24)
                .setContentIntent(pendingIntent)
                .build()

        notificationManager.notify(movieId, notification)
    }
}