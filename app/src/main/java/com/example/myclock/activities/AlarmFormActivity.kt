package com.example.myclock.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myclock.R
import com.example.myclock.dialogfragments.LabelInputDialog
import com.example.myclock.interfaces.DialogButtonsListener
import com.example.myclock.models.Alarm
import com.google.android.material.switchmaterial.SwitchMaterial

class AlarmFormActivity : AppCompatActivity() {
    private val alarm = Alarm()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_form)
        initToolbar()
        initLabelFieldLayout()
        initLabelFieldLayout()
        initVibrateField()
    }

    private fun initToolbar() {
        setSupportActionBar(findViewById(R.id.tbAlarmForm))
        supportActionBar?.title = "Add Alarm"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
    }

    private fun initLabelFieldLayout() {
        val llLabelField = findViewById<LinearLayout>(R.id.llLabelField)
        llLabelField.setOnClickListener {
            val labelInputDialog = LabelInputDialog()

            labelInputDialog.setDialogButtonsListener(object : DialogButtonsListener {
                override fun onDialogPositiveClick(any: Any) {
                    alarm.label = any as String
                    findViewById<TextView>(R.id.tvLabel)?.text = alarm.label
                }

                override fun onDialogNegativeClick(any: Any) {
                    TODO("Not yet implemented")
                }
            })

            labelInputDialog.show(supportFragmentManager, "LABEL_INPUT_DIALOG")
        }
    }

    private fun initVibrateField() {
        val smVibrate = findViewById<SwitchMaterial>(R.id.smVibrate)
        smVibrate.setOnCheckedChangeListener { _, isChecked ->
            alarm.vibrate = isChecked
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