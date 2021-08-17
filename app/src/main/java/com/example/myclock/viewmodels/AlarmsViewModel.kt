package com.example.myclock.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myclock.fragments.AlarmsFragmentDirections
import com.example.myclock.models.Alarm
import com.example.myclock.repositories.AlarmsRepository
import com.example.myclock.utilities.Utility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmsViewModel @Inject constructor(
    private val alarmsRepository: AlarmsRepository
) : ViewModel() {
    var alarmsLiveData = alarmsRepository.load().asLiveData()
    var alarmFormLiveData = MutableLiveData<Alarm>()

    init {
        alarmFormLiveData.value = Alarm()
    }

    fun save() {
        viewModelScope.launch {
            alarmsRepository.save(alarmFormLiveData.value!!)
        }
    }
}