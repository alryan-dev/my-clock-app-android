package com.example.myclock.utilities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationUtils {
    const val FIRE_ALARM_CHANNEL_ID = "firing_alarms"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Firing alarms"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(FIRE_ALARM_CHANNEL_ID, name, importance)

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}