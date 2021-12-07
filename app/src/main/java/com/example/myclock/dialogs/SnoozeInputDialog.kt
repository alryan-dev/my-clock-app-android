package com.example.myclock.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.myclock.databinding.DialogSnoozeInputBinding
import com.example.myclock.models.Alarm
import com.example.myclock.utilities.SnoozeUtils
import com.example.myclock.viewmodels.AlarmsViewModel

class SnoozeInputDialog : DialogFragment() {
    private val alarmsViewModel: AlarmsViewModel by activityViewModels()
    private var _binding: DialogSnoozeInputBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogSnoozeInputBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        val alarm = Alarm.copy(alarmsViewModel.selectedAlarm.value)
        binding.snoozeDurationValue = SnoozeUtils.getSnoozeDurationSliderValue(alarm.snoozeDuration)
        binding.noOfSnoozesValue = SnoozeUtils.getNoOfSnoozesSliderValue(alarm.noOfSnoozes)

        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setTitle("Snooze duration")
                .setView(binding.root)
                .setPositiveButton("OK") { _, _ ->
                    binding.snoozeDurationValue.let { snoozeDurationValue ->
                        alarm.snoozeDuration =
                            SnoozeUtils.getSnoozeDurationValue(snoozeDurationValue)
                    }
                    binding.noOfSnoozesValue.let { noOfSnoozesValue ->
                        alarm.noOfSnoozes = SnoozeUtils.getNoOfSnoozesValue(noOfSnoozesValue)
                    }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}