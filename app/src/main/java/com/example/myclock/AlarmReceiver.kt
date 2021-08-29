package com.example.myclock

import android.app.KeyguardManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myclock.activities.AlarmDisplayActivity
import com.example.myclock.utilities.NotificationUtils
import com.example.myclock.utilities.Utility

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras

        // Activity to display
        val alarmDisplayIntent = Intent(context, AlarmDisplayActivity::class.java).apply {
            putExtra("alarm_label", bundle?.getString("alarm_label"))
            putExtra("alarm_time", bundle?.getString("alarm_time"))
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val alarmDisplayPendingIntent = PendingIntent.getActivity(
            context,
            0,
            alarmDisplayIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Set notification content
        val builder = NotificationCompat.Builder(context, NotificationUtils.FIRE_ALARM_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_alarm_on)
            .setContentTitle(bundle?.getString("alarm_label"))
            .setContentText(bundle?.getString("alarm_time"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)

        // Check if device is locked
        val keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        val isDeviceLocked: Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            keyguardManager.isDeviceLocked
        } else {
            keyguardManager.inKeyguardRestrictedInputMode()
        }

        if (isDeviceLocked) {
            builder.setFullScreenIntent(alarmDisplayPendingIntent, true)
        } else {
            builder.setContentIntent(alarmDisplayPendingIntent)
        }

        // Show notification
        bundle?.let {
            with(NotificationManagerCompat.from(context)) {
                notify(it.getInt("alarm_id"), builder.build())
            }
        }
    }
}