package com.example.myclock.modules

import android.content.Context
import androidx.room.Room
import com.example.myclock.database.AppDatabase
import com.example.myclock.database.daos.AlarmDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "myclockdb"
        ).enableMultiInstanceInvalidation().build()
    }

    @Provides
    fun provideAlarmDao(appDatabase: AppDatabase): AlarmDao {
        return appDatabase.alarmDao()
    }
}