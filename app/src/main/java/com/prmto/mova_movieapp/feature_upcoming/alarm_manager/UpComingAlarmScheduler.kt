package com.prmto.mova_movieapp.feature_upcoming.alarm_manager

interface UpComingAlarmScheduler {
    fun scheduleAlarm(item: UpComingAlarmItem)
    fun cancelAlarm(item: UpComingAlarmItem)
}