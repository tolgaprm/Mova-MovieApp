package com.prmto.mova_movieapp.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi

abstract class Notifier(
    private val notificationManager: NotificationManager
) {
    abstract val notificationChannelId: String
    abstract val notificationChannelName: String
    abstract val notificationId: Int

    fun showNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = createNotificationChannel()
            notificationManager.createNotificationChannel(channel)
        }

        val notification =
            buildNotification()
        notificationManager.notify(
            notificationId,
            notification
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun createNotificationChannel(
        importance: Int = NotificationManager.IMPORTANCE_DEFAULT
    ): NotificationChannel {
        return NotificationChannel(
            notificationChannelId,
            notificationChannelName,
            importance
        )
    }

    abstract fun buildNotification(): Notification

    protected abstract fun getNotificationTitle(): String

    protected abstract fun getNotificationMessage(): String
}