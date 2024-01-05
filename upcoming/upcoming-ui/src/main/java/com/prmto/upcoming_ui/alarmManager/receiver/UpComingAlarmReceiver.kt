package com.prmto.upcoming_ui.alarmManager.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.prmto.mova_movieapp.notification.Notifier
import com.prmto.mova_movieapp.notification.di.UpcomingMovieReminderNotifierQualifier
import com.prmto.notification.variants.UpcomingMovieReminderNotifier
import com.prmto.upcoming_ui.alarmManager.AndroidUpComingAlarmScheduler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UpComingAlarmReceiver : BroadcastReceiver() {

    @Inject
    @UpcomingMovieReminderNotifierQualifier
    lateinit var notifier: Notifier

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            intent?.let {
                val movieId = intent.getIntExtra(AndroidUpComingAlarmScheduler.EXTRA_MOVIE_ID, -1)
                val movieTitle =
                    intent.getStringExtra(AndroidUpComingAlarmScheduler.EXTRA_MOVIE_TITLE)

                if (movieId == -1) return
                val upcomingMovieReminderNotifier = notifier as UpcomingMovieReminderNotifier
                upcomingMovieReminderNotifier.setMovieId(movieId)
                upcomingMovieReminderNotifier.setMovieTitle(movieTitle ?: "")
                upcomingMovieReminderNotifier.showNotification()
            }
        } ?: return
    }
}