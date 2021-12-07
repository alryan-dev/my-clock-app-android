package com.example.myclock.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.myclock.databinding.DialogFieldInputBinding
import com.example.myclock.models.Alarm
import com.example.myclock.utilities.Utility
import com.example.myclock.viewmodels.AlarmsViewModel
import java.util.Calendar.HOUR_OF_DAY

class LabelInputDialog : DialogFragment() {
    private val alarmsViewModel: AlarmsViewModel by activityViewModels()
    private var _binding: DialogFieldInputBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogFieldInputBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.alarm = Alarm.copy(alarmsViewModel.selectedAlarm.value)

        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setTitle("Label")
                .setView(binding.root)
                .setPositiveButton("OK") { _, _ ->
                    binding.alarm?.let { alarm ->
                        alarmsViewModel.selectedAlarm.value = alarm
                    }
                }
                .setNegativeButton("CANCEL", null)

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onResume() {
        super.onResume()
        val window = dialog?.window

        // Set dialog width to screen width
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        // Show keyboard
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        Utility.showKeyboard(context, binding.etLabel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}