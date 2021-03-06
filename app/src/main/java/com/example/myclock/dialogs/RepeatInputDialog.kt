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

class RepeatInputDialog : DialogFragment() {
    private val alarmsViewModel: AlarmsViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alarm = Alarm.copy(alarmsViewModel.selectedAlarm.value)

        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setTitle("Repeat")
                .setMultiChoiceItems(Utility.weekdays, alarm.repeat) { _, which, isChecked ->
                    alarm.repeat[which] = isChecked
                }
                .setPositiveButton("OK") { _, _ ->
                    alarmsViewModel.selectedAlarm.value = alarm
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