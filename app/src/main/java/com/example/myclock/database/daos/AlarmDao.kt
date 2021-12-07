package com.example.myclock.database.daos

import androidx.room.*
import com.example.myclock.models.Alarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarm ORDER BY time DESC")
    suspend fun load(): List<Alarm>

    @Insert
    suspend fun insert(alarm: Alarm): Long

    @Update
    suspend fun update(alarm: Alarm)

    @Delete
    suspend fun delete(user: Alarm)
}