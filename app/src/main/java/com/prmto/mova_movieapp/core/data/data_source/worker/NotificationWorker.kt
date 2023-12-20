package com.prmto.mova_movieapp.core.data.data_source.worker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.prmto.mova_movieapp.di.WeekendNotifierQualifier
import com.prmto.mova_movieapp.notification.Notifier
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @WeekendNotifierQualifier private val notifier: Notifier
) : Worker(
    appContext,
    workerParams
) {
    override fun doWork(): Result {
        return if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(
                    appContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Result.failure()
            } else {
                notifier.showNotification()
                Result.success()
            }
        } else {
            notifier.showNotification()
            Result.success()
        }
    }

}

