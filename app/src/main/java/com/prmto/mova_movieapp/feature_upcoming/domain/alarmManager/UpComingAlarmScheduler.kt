package com.prmto.mova_movieapp.feature_upcoming.domain.alarmManager

import com.prmto.mova_movieapp.feature_upcoming.alarm_manager.UpComingAlarmItem

interface UpComingAlarmScheduler {
    fun scheduleAlarm(item: UpComingAlarmItem)
    fun cancelAlarm(item: UpComingAlarmItem)
}