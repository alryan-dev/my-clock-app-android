package com.example.myclock.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myclock.R
import com.example.myclock.databinding.FragmentAlarmFormBinding
import com.example.myclock.models.Alarm
import com.example.myclock.viewmodels.AlarmsViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AlarmFormFragment : Fragment() {
    private val alarmsViewModel: AlarmsViewModel by activityViewModels()
    private var _binding: FragmentAlarmFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlarmFormBinding.inflate(inflater, container, false)
        binding.alarmsViewModel = alarmsViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Set up views
        setHasOptionsMenu(true)
        setUpTimeField()

        binding.llRepeatField.setOnClickListener {
            val action = AlarmFormFragmentDirections.actionAlarmFormFragmentToRepeatInputDialog()
            findNavController().navigate(action)
        }

        binding.llLabelField.setOnClickListener {
            val action = AlarmFormFragmentDirections.actionAlarmFormFragmentToLabelInputDialog()
            findNavController().navigate(action)
        }

        binding.llRingDurationField.setOnClickListener {
            val action =
                AlarmFormFragmentDirections.actionAlarmFormFragmentToRingDurationInputDialog()
            findNavController().navigate(action)
        }

        binding.llSnoozeDurationField.setOnClickListener {
            val action = AlarmFormFragmentDirections.actionAlarmFormFragmentToSnoozeInputDialog()
            findNavController().navigate(action)
        }
    }

    private fun setUpTimeField() {
        binding.llTimeField.setOnClickListener {
            val picker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setTitleText("Set Time")
                    .setHour(alarmsViewModel.selectedAlarm.value.time.get(Calendar.HOUR_OF_DAY))
                    .setMinute(alarmsViewModel.selectedAlarm.value.time.get(Calendar.MINUTE))
                    .build()

            picker.addOnPositiveButtonClickListener {
                alarmsViewModel.selectedAlarm.value =
                    Alarm.copy(alarmsViewModel.selectedAlarm.value).apply {
                        time.set(Calendar.HOUR_OF_DAY, picker.hour)
                        time.set(Calendar.MINUTE, picker.minute)
                        time.set(Calendar.SECOND, 0)
                        time.set(Calendar.MILLISECOND, 0)
                    }
            }

            picker.show(childFragmentManager, "TIME_INPUT_DIALOG")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_alarm_form, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_save -> {
            alarmsViewModel.save(alarmsViewModel.selectedAlarm.value)
            findNavController().popBackStack()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}