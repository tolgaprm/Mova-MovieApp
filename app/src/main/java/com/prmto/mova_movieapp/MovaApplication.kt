package com.prmto.mova_movieapp

import android.app.Application
import android.icu.util.Calendar
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
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

        val notificationWorker =
            OneTimeWorkRequestBuilder<com.prmto.work_manager.NotificationWorker>()
                .build()

        val updateFirebaseMovieWorker =
            PeriodicWorkRequestBuilder<com.prmto.work_manager.UpdateFirebaseMovieWorker>(
                repeatInterval = 1, repeatIntervalTimeUnit = TimeUnit.DAYS
            ).setConstraints(constraints).build()

        val updateFirebaseTvSeriesWorker =
            PeriodicWorkRequestBuilder<com.prmto.work_manager.UpdateFirebaseTvSeriesWorker>(
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
            workManager.beginUniqueWork(
                "notification_worker",
                ExistingWorkPolicy.REPLACE,
                notificationWorker
            ).enqueue()
        }
    }
}