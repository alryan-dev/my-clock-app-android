package com.example.myclock.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
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
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data != null) {
                    val alarm = data.getParcelableExtra<Alarm>("ALARM")
                    alarm?.let {
                        alarmsList.add(it)
                        alarmsRvAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

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
        alarm1.label = "Alarm 1"
        alarmsList.add(alarm1)

        val alarm2 = Alarm()
        alarm2.label = "Alarm 2"
        alarmsList.add(alarm2)

        val alarm3 = Alarm()
        alarm3.label = "Alarm 3"
        alarmsList.add(alarm3)

        alarmsRvAdapter = AlarmsRvAdapter(alarmsList)
        rvAlarms.adapter = alarmsRvAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_alarms, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_add -> {
            val intent = Intent(this, AlarmFormActivity::class.java)
            startForResult.launch(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}