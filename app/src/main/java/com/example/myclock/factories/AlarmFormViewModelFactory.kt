package com.example.myclock.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myclock.models.Alarm
import com.example.myclock.viewmodels.AlarmFormViewModel

class AlarmFormViewModelFactory(private val alarm: Alarm) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlarmFormViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlarmFormViewModel(alarm) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}