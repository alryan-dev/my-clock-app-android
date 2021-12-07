package com.example.myclock.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myclock.repositories.AlarmsRepository
import com.example.myclock.viewmodels.AlarmsViewModel
import javax.inject.Inject

class AlarmsViewModelFactory @Inject constructor(
    private val alarmsRepository: AlarmsRepository,
    private val application: Application
) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlarmsViewModel::class.java)) {
            return AlarmsViewModel(alarmsRepository, application) as T
        }

        throw IllegalArgumentException("Incorrect ViewModel class")
    }
}