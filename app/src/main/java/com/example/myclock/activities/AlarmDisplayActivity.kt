package com.example.myclock.activities

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myclock.R

class AlarmDisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_display)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setTurnScreenOn(true)
            setShowWhenLocked(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }

        val bundle = intent.extras

        val tvAlarmLabel = findViewById<TextView>(R.id.tvAlarmLabel)
        tvAlarmLabel.text = bundle?.getString("alarm_label")

        val tvAlarmTime = findViewById<TextView>(R.id.tvAlarmTime)
        tvAlarmTime.text = bundle?.getString("alarm_time")
    }
}