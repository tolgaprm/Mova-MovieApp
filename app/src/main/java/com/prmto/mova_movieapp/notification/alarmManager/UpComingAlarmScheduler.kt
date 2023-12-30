package com.prmto.mova_movieapp.notification.alarmManager

interface UpComingAlarmScheduler {
    fun scheduleAlarm(item: UpComingAlarmItem)
    fun cancelAlarm(item: UpComingAlarmItem)
}