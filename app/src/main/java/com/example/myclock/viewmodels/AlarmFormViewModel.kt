package com.example.myclock.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myclock.models.Alarm

class AlarmFormViewModel(alarm: Alarm) : ViewModel() {
    var alarmLiveData = MutableLiveData<Alarm>()

    init {
        alarmLiveData.value = alarm
    }
}