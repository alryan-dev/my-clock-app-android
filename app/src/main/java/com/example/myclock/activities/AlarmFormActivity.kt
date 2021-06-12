package com.example.myclock.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myclock.R
import com.example.myclock.utilities.Utility
import com.example.myclock.dialogs.LabelInputDialog
import com.example.myclock.dialogs.RepeatInputDialog
import com.example.myclock.dialogs.RingDurationInputDialog
import com.example.myclock.dialogs.SnoozeInputDialog
import com.example.myclock.factories.AlarmFormViewModelFactory
import com.example.myclock.models.Alarm
import com.example.myclock.utilities.SnoozeUtils
import com.example.myclock.viewmodels.AlarmFormViewModel
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.gson.Gson
import java.util.*

class AlarmFormActivity : AppCompatActivity() {
    private lateinit var alarm: Alarm
    private val alarmFormViewModel: AlarmFormViewModel by viewModels {
        AlarmFormViewModelFactory(alarm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_form)
        alarm = intent.getParcelableExtra("ALARM") ?: Alarm()
        alarmFormViewModel.alarmLiveData.observe(this, {
            alarm = it
            Utility.print(Gson().toJson(alarm))
        })

        initToolbar()
        initTimeField()
        initRepeatField()
        initVibrateField()
        initLabelField()
        initRingDurationField()
        initSnoozeField()
    }

    private fun initToolbar() {
        setSupportActionBar(findViewById(R.id.tbAlarmForm))
        supportActionBar?.title = "Add Alarm"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
    }

    private fun initTimeField() {
        val tvTime = findViewById<TextView>(R.id.tvTime)
        tvTime.text = Utility.timeToString(alarm.time)

        val llTimeField = findViewById<LinearLayout>(R.id.llTimeField)
        llTimeField.setOnClickListener {
            val picker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setTitleText("Set Time")
                    .setHour(alarm.time.get(Calendar.HOUR_OF_DAY))
                    .setMinute(alarm.time.get(Calendar.MINUTE))
                    .build()

            picker.addOnPositiveButtonClickListener {
                alarm.time.set(Calendar.HOUR_OF_DAY, picker.hour)
                alarm.time.set(Calendar.MINUTE, picker.minute)
                tvTime.text = Utility.timeToString(alarm.time)
            }

            picker.show(supportFragmentManager, "TIME_INPUT_DIALOG")
        }
    }

    private fun initRepeatField() {
        val tvRepeat = findViewById<TextView>(R.id.tvRepeat)
        tvRepeat.text = Utility.getRepeatLabel(alarm.repeat)

        val llRepeatField = findViewById<LinearLayout>(R.id.llRepeatField)
        llRepeatField.setOnClickListener {
            val repeatInputDialog = RepeatInputDialog()
            repeatInputDialog.dialogInterface = object : DialogInterface {
                override fun cancel() {}

                override fun dismiss() {
                    tvRepeat.text = Utility.getRepeatLabel(alarm.repeat)
                }
            }

            repeatInputDialog.show(supportFragmentManager, "REPEAT_INPUT_DIALOG")
        }
    }

    private fun initLabelField() {
        val tvLabel = findViewById<TextView>(R.id.tvLabel)
        tvLabel.text = alarm.label

        val llLabelField = findViewById<LinearLayout>(R.id.llLabelField)
        llLabelField.setOnClickListener {
            val labelInputDialog = LabelInputDialog()
            labelInputDialog.dialogInterface = object : DialogInterface {
                override fun cancel() {
                    TODO("Not yet implemented")
                }

                override fun dismiss() {
                    tvLabel.text = alarm.label
                }
            }

            labelInputDialog.show(supportFragmentManager, "LABEL_INPUT_DIALOG")
        }
    }

    private fun initVibrateField() {
        val smVibrate = findViewById<SwitchMaterial>(R.id.smVibrate)
        smVibrate.isChecked = alarm.vibrate
        smVibrate.setOnCheckedChangeListener { _, isChecked ->
            alarm.vibrate = isChecked
        }

        val llVibrateField = findViewById<LinearLayout>(R.id.llVibrateField)
        llVibrateField.setOnClickListener {
            smVibrate.isChecked = !alarm.vibrate
        }
    }

    private fun initRingDurationField() {
        val tvRingDuration = findViewById<TextView>(R.id.tvRingDuration)
        tvRingDuration.text = Utility.getRingDurationLabel(alarm.ringDuration)

        val llRingDurationField = findViewById<LinearLayout>(R.id.llRingDurationField)
        llRingDurationField.setOnClickListener {
            val ringDurationInputDialog = RingDurationInputDialog()
            ringDurationInputDialog.dialogInterface = object : DialogInterface {
                override fun cancel() {}

                override fun dismiss() {
                    tvRingDuration.text = Utility.getRingDurationLabel(alarm.ringDuration)
                }
            }

            ringDurationInputDialog.show(supportFragmentManager, "RING_DURATION_INPUT_DIALOG")
        }
    }

    private fun initSnoozeField() {
        val tvSnoozeDuration = findViewById<TextView>(R.id.tvSnoozeDuration)
        (SnoozeUtils.getSnoozeDurationLabel(alarm.snoozeDuration) + ", " + SnoozeUtils.getNoOfSnoozesLabel(
            alarm.noOfSnoozes
        )).also { tvSnoozeDuration.text = it }

        val llSnoozeDurationField = findViewById<LinearLayout>(R.id.llSnoozeDurationField)
        llSnoozeDurationField.setOnClickListener {
            val snoozeInputDialog = SnoozeInputDialog()
            snoozeInputDialog.isCancelable = false

            snoozeInputDialog.dialogInterface = object : DialogInterface {
                override fun cancel() {}

                override fun dismiss() {
                    (SnoozeUtils.getSnoozeDurationLabel(alarm.snoozeDuration) + ", " + SnoozeUtils.getNoOfSnoozesLabel(
                        alarm.noOfSnoozes
                    )).also { tvSnoozeDuration.text = it }
                }
            }

            snoozeInputDialog.show(supportFragmentManager, "SNOOZE_INPUT_DIALOG")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_alarm_form, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_save -> {
            val prevIntent = Intent()
            prevIntent.putExtra("ALARM", alarm)
            setResult(RESULT_OK, prevIntent)
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}