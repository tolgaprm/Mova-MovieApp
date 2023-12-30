package com.prmto.mova_movieapp.feature_upcoming.alarm_manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.prmto.core_domain.util.DateFormatUtils
import com.prmto.mova_movieapp.feature_upcoming.domain.alarmManager.UpComingAlarmScheduler
import java.time.LocalDate
import java.time.ZoneId

class AndroidUpComingAlarmScheduler(
    private val context: Context
) : UpComingAlarmScheduler {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    override fun scheduleAlarm(item: UpComingAlarmItem) {

        val movaDate =
            DateFormatUtils.convertToDateFromReleaseDate(releaseDate = item.movieReleaseDate)

        val triggerAtMillis = LocalDate.of(
            movaDate.year,
            movaDate.month,
            movaDate.dayOfMonth
        ).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val intent = Intent(context, UpComingAlarmReceiver::class.java).apply {
            putExtra(EXTRA_MOVIE_ID, item.movieId)
            putExtra(EXTRA_MOVIE_TITLE, item.movieTitle)
        }

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            PendingIntent.getBroadcast(
                context,
                item.movieId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    companion object {
        const val EXTRA_MOVIE_ID = "movieId"
        const val EXTRA_MOVIE_TITLE = "movieTitle"
    }

    override fun cancelAlarm(item: UpComingAlarmItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.movieId,
                Intent(context, UpComingAlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}