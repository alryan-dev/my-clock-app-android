package com.example.myclock.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myclock.models.Alarm
import com.example.myclock.repositories.AlarmsRepository
import com.example.myclock.utilities.AlarmManagerHelper
import com.example.myclock.utilities.Utility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmsViewModel @Inject constructor(
    private val alarmsRepository: AlarmsRepository,
    app: Application
) : AndroidViewModel(app) {
    private val _alarms = MutableStateFlow<MutableList<Alarm>>(mutableListOf())
    val alarms: StateFlow<List<Alarm>> get() = _alarms
    var selectedAlarm = MutableStateFlow(Alarm())

    init {
        loadAlarms()
    }

    private fun loadAlarms() {
        viewModelScope.launch {
            _alarms.value = alarmsRepository.alarmDao.load().toMutableList()
        }
    }

    fun save(alarm: Alarm) {
        viewModelScope.launch {
            var id = alarmsRepository.save(alarm).toInt()
            if (alarm.id > 0 && id == 0) id = alarm.id
            loadAlarms()
            if (alarm.enabled) AlarmManagerHelper.setAlarm(id, alarm, getApplication())
        }
    }

    fun toggleAlarmStatus(alarm: Alarm) {
        viewModelScope.launch {
            alarmsRepository.save(alarm)
            if (alarm.enabled) AlarmManagerHelper.setAlarm(alarm.id, alarm, getApplication())
            else AlarmManagerHelper.cancelAlarm(alarm, getApplication())
        }
    }

    fun deleteAlarm(alarm: Alarm) {
        viewModelScope.launch {
            alarmsRepository.delete(alarm)
            _alarms.value.remove(alarm)
            AlarmManagerHelper.cancelAlarm(alarm, getApplication())
        }
    }
}