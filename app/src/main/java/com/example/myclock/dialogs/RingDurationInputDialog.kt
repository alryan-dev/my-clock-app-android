package com.example.myclock.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.myclock.models.Alarm
import com.example.myclock.utilities.Utility
import com.example.myclock.viewmodels.AlarmsViewModel

class RingDurationInputDialog : DialogFragment() {
    private val alarmsViewModel: AlarmsViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alarm = Alarm.copy(alarmsViewModel.selectedAlarm.value)
        val checkedItem = when (alarm.ringDuration) {
            1 -> 0
            5 -> 1
            10 -> 2
            15 -> 3
            20 -> 4
            else -> 5
        }

        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setTitle("Repeat")
                .setSingleChoiceItems(
                    Utility.durations,
                    checkedItem
                ) { _, which ->
                    alarm.ringDuration = when (which) {
                        0 -> 1
                        1 -> 5
                        2 -> 10
                        3 -> 15
                        4 -> 20
                        else -> 30
                    }
                    alarmsViewModel.selectedAlarm.value = alarm
                    dismiss()
                }
                .setNegativeButton("CANCEL", null)

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onResume() {
        super.onResume()
        // Set dialog width to screen width
        val window = dialog?.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}