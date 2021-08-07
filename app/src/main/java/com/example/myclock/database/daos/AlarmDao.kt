package com.example.myclock.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myclock.models.Alarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarm")
    fun load(): Flow<List<Alarm>>

    @Insert
    suspend fun save(alarm: Alarm)

    @Delete
    fun delete(user: Alarm)
}