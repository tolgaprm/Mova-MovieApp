package com.prmto.work_manager

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.prmto.mova_movieapp.notification.Notifier
import com.prmto.mova_movieapp.notification.di.WeekendNotifierQualifier
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

