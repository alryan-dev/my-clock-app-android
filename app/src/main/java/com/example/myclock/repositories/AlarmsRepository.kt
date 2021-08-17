package com.example.myclock.repositories

import com.example.myclock.database.daos.AlarmDao
import com.example.myclock.models.Alarm
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmsRepository @Inject constructor(
    private val alarmDao: AlarmDao
) {
    fun load(): Flow<List<Alarm>> {
        return alarmDao.load()
    }

    suspend fun save(alarm: Alarm) {
        if (alarm.id > 0) alarmDao.update(alarm)
        else alarmDao.insert(alarm)
    }
}