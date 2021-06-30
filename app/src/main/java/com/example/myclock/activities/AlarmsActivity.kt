package com.example.myclock.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myclock.R
import com.example.myclock.adapters.AlarmsRvAdapter
import com.example.myclock.models.Alarm
import com.example.myclock.viewmodels.AlarmsViewModel

class AlarmsActivity : AppCompatActivity() {
    private var alarmsList = mutableListOf<Alarm>()
    private lateinit var alarmsRvAdapter: AlarmsRvAdapter
    private val alarmsViewModel: AlarmsViewModel by viewModels()
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
        initAlarmsRecyclerView()
        alarmsViewModel.alarmsLiveData.observe(this, {
            alarmsList.addAll(it)
            alarmsRvAdapter.notifyDataSetChanged()
        })

        // Init toolbar
        setSupportActionBar(findViewById(R.id.tbAlarms))
        supportActionBar?.title = "Alarms"
    }

    private fun initAlarmsRecyclerView() {
        // Init recyclerview
        val rvAlarms = findViewById<RecyclerView>(R.id.rvAlarms)
        rvAlarms.layoutManager = LinearLayoutManager(this)
        rvAlarms.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        // Init adapter
        alarmsRvAdapter = AlarmsRvAdapter(this, alarmsList, alarmsViewModel)
        rvAlarms.adapter = alarmsRvAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_alarms, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_alarmsFragment_to_alarmFormFragment -> {
            val intent = Intent(this, AlarmFormActivity::class.java)
            startForResult.launch(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}