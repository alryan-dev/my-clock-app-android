package com.example.myclock.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myclock.R
import com.example.myclock.adapters.AlarmsRvAdapter
import com.example.myclock.models.Alarm
import java.util.*

class AlarmsActivity : AppCompatActivity() {
    private val alarmsList = mutableListOf<Alarm>()
    private lateinit var alarmsRvAdapter: AlarmsRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarms)
        initAlarms()

        // Init toolbar
        setSupportActionBar(findViewById(R.id.tbAlarms))
        supportActionBar?.title = "Alarms"
    }

    private fun initAlarms() {
        val rvAlarms = findViewById<RecyclerView>(R.id.rvAlarms)
        rvAlarms.layoutManager = LinearLayoutManager(this)
        rvAlarms.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        val alarm1 = Alarm()
        alarm1.time = Date()
        alarm1.label = "Alarm 1"
        alarmsList.add(alarm1)

        val alarm2 = Alarm()
        alarm2.time = Date()
        alarm2.label = "Alarm 2"
        alarmsList.add(alarm2)

        val alarm3 = Alarm()
        alarm3.time = Date()
        alarm3.label = "Alarm 3"
        alarmsList.add(alarm3)

        alarmsRvAdapter = AlarmsRvAdapter(alarmsList)
        rvAlarms.adapter = alarmsRvAdapter
    }
}