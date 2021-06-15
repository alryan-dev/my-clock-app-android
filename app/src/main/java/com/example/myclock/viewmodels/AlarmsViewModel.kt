package com.example.myclock.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myclock.models.Alarm

class AlarmsViewModel : ViewModel() {
    var alarmsLiveData = MutableLiveData<MutableList<Alarm>>()

    init {
        val alarmsList = mutableListOf<Alarm>()

        val alarm1 = Alarm()
        alarm1.label = "Alarm 1"
        alarmsList.add(alarm1)

        val alarm2 = Alarm()
        alarm2.label = "Alarm 2"
        alarmsList.add(alarm2)

        val alarm3 = Alarm()
        alarm3.label = "Alarm 3"
        alarmsList.add(alarm3)

        alarmsLiveData.value = alarmsList
    }
}