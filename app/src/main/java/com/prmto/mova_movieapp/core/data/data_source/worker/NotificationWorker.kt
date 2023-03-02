package com.prmto.mova_movieapp.core.data.data_source.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.prmto.mova_movieapp.MainActivity
import com.prmto.mova_movieapp.R

class NotificationWorker(
 private val appContext:Context,
    workerParams:WorkerParameters

):Worker(
    appContext,
    workerParams
) {
    override fun doWork(): Result {
        return if (Build.VERSION.SDK_INT >=33){
            if (ContextCompat.checkSelfPermission(appContext,Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                Result.failure()
            }else{
                showNotification(appContext)
                Result.success()
            }
        }else{
            showNotification(appContext)
            Result.success()
        }
    }
}

private fun showNotification(appContext: Context){
    val notificationManager = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
        val channel = NotificationChannel(
            "notification_channel",
            "Channel name",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
    }

    val intent = Intent(appContext, MainActivity::class.java)

    val pendingIntent =  PendingIntent.getActivity(appContext,1,intent,
        PendingIntent.FLAG_IMMUTABLE
    )

    val notification = NotificationCompat.Builder(appContext,"notification_channel")
        .setContentTitle(appContext.getString(R.string.have_good_weekend))
        .setContentText(appContext.getString(R.string.movie_notification))
        .setSmallIcon(R.drawable.ic_outline_movie_24)
        .setContentIntent(pendingIntent)
        .build()

    notificationManager.notify(1,notification)

}