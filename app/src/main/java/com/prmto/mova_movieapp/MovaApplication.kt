package com.prmto.mova_movieapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.google.type.DateTime
import com.prmto.mova_movieapp.core.data.data_source.worker.NotificationWorker
import com.prmto.mova_movieapp.core.data.data_source.worker.UpdateFirebaseMovieWorker
import com.prmto.mova_movieapp.core.data.data_source.worker.UpdateFirebaseTvSeriesWorker
import com.prmto.mova_movieapp.feature_person_detail.domain.util.DateFormatUtils
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltAndroidApp
class MovaApplication @Inject constructor(
) : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()


        val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setConstraints(constraints)
            .build()

        val updateFirebaseMovieWorker = PeriodicWorkRequestBuilder<UpdateFirebaseMovieWorker>(
            repeatInterval = 1, repeatIntervalTimeUnit = TimeUnit.DAYS
        ).setConstraints(constraints).build()

        val updateFirebaseTvSeriesWorker = PeriodicWorkRequestBuilder<UpdateFirebaseTvSeriesWorker>(
            repeatInterval = 1, repeatIntervalTimeUnit = TimeUnit.DAYS
        ).setConstraints(constraints).build()

        val workManager = WorkManager.getInstance(this)

        workManager.enqueueUniquePeriodicWork(
            "FirebaseMovieWork",
            ExistingPeriodicWorkPolicy.KEEP,
            updateFirebaseMovieWorker
        )
        workManager.enqueueUniquePeriodicWork(
            "FirebaseTvSeriesWork",
            ExistingPeriodicWorkPolicy.KEEP,
            updateFirebaseTvSeriesWorker
        )

        val calendar = Calendar.getInstance()
        if (calendar.isWeekend){
            workManager.beginUniqueWork("notification_worker",
            ExistingWorkPolicy.KEEP,
            notificationWorker).enqueue()
        }
    }
}