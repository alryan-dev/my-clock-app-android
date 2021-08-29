package com.example.myclock

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.myclock.utilities.NotificationUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Disable night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Create notification channels
        NotificationUtils.createNotificationChannel(applicationContext)
    }
}