package com.example.myclock.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.myclock.R
import com.example.myclock.utilities.SnoozeUtils
import com.example.myclock.viewmodels.AlarmsViewModel
import com.google.android.material.slider.Slider

class SnoozeInputDialog : DialogFragment() {
    private val alarmsViewModel: AlarmsViewModel by activityViewModels()
    lateinit var dialogInterface: DialogInterface

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alarm = alarmsViewModel.alarmLiveData.value
        val view = layoutInflater.inflate(R.layout.dialog_snooze_input, null)
        val sSnoozeDuration = view.findViewById<Slider>(R.id.sSnoozeDuration)
        val sNoOfSnoozes = view.findViewById<Slider>(R.id.sNoOfSnoozes)

        sSnoozeDuration.value = SnoozeUtils.getSnoozeDurationSliderValue(alarm?.snoozeDuration)
        sNoOfSnoozes.value = SnoozeUtils.getNoOfSnoozesSliderValue(alarm?.noOfSnoozes)

        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setTitle("Snooze duration")
                .setView(view)
                .setPositiveButton("OK") { _, _ ->
                    alarm?.snoozeDuration = SnoozeUtils.getSnoozeDurationValue(sSnoozeDuration.value)
                    alarm?.noOfSnoozes = SnoozeUtils.getNoOfSnoozesValue(sNoOfSnoozes.value)
                    alarmsViewModel.alarmLiveData.value = alarm
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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dialogInterface.dismiss()
    }
}