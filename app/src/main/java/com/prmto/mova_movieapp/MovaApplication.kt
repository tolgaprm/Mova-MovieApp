package com.prmto.mova_movieapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.prmto.mova_movieapp.core.data.data_source.FirebaseMovieWorker
import com.prmto.mova_movieapp.core.data.data_source.FirebaseTvSeriesWorker
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

        val firebaseMovieWorker = PeriodicWorkRequestBuilder<FirebaseMovieWorker>(
            repeatInterval = 5, repeatIntervalTimeUnit = TimeUnit.HOURS
        ).setConstraints(constraints).build()

        val firebaseTvSeriesWorker = PeriodicWorkRequestBuilder<FirebaseTvSeriesWorker>(
            repeatInterval = 5, repeatIntervalTimeUnit = TimeUnit.HOURS
        ).setConstraints(constraints).build()

        val workManager = WorkManager.getInstance(this)

        workManager.enqueueUniquePeriodicWork(
            "FirebaseMovieWork",
            ExistingPeriodicWorkPolicy.KEEP,
            firebaseMovieWorker
        )
        workManager.enqueueUniquePeriodicWork(
            "FirebaseTvSeriesWork",
            ExistingPeriodicWorkPolicy.KEEP,
            firebaseTvSeriesWorker
        )
    }
}