package com.example.myclock.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myclock.database.daos.AlarmDao
import com.example.myclock.models.Alarm

@Database(entities = [Alarm::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}