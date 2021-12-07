package com.example.myclock.repositories

import com.example.myclock.database.daos.AlarmDao
import com.example.myclock.models.Alarm
import javax.inject.Inject

class AlarmsRepository @Inject constructor(
    val alarmDao: AlarmDao
) {
    suspend fun save(alarm: Alarm): Long = if (alarm.id > 0) {
        alarmDao.update(alarm)
        0
    } else {
        alarmDao.insert(alarm)
    }

    suspend fun delete(alarm: Alarm) {
        alarmDao.delete(alarm)
    }
}