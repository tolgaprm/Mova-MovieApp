package com.prmto.upcoming_ui.alarmManager

interface UpComingAlarmScheduler {
    fun scheduleAlarm(item: UpComingAlarmItem)
    fun cancelAlarm(item: UpComingAlarmItem)
}