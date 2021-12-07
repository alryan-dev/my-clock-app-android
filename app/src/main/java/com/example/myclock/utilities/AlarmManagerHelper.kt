package com.example.myclock.utilities

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.myclock.AlarmReceiver
import com.example.myclock.models.Alarm

object AlarmManagerHelper {

    private fun createIntent(id: Int, alarm: Alarm, application: Application): Intent {
        return Intent(application, AlarmReceiver::class.java).apply {
            putExtra("alarm_id", id)
            putExtra("alarm_label", alarm.label)
            putExtra("alarm_time", Utility.timeToString(alarm.time))
        }
    }

    private fun createPendingIntent(
        intent: Intent,
        application: Application,
        id: Int
    ): PendingIntent {
        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        return PendingIntent.getBroadcast(application, id, intent, flag)
    }

    fun setAlarm(id: Int, alarm: Alarm, application: Application) {
        // Create Intent and PendingIntent
        val intent = createIntent(id, alarm, application)
        val pendingIntent = createPendingIntent(intent, application, id)

        // Set alarm
        val alarmClockInfo = AlarmManager.AlarmClockInfo(alarm.time.timeInMillis, null)
        val alarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setAlarmClock(alarmClockInfo, pendingIntent)
    }

    fun cancelAlarm(alarm: Alarm, application: Application) {
        // Create Intent and PendingIntent
        val intent = createIntent(alarm.id, alarm, application)
        val pendingIntent = createPendingIntent(intent, application, alarm.id)

        // Cancel Alarm
        val alarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}