package com.example.myclock.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myclock.R
import com.example.myclock.dialogs.LabelInputDialog
import com.example.myclock.dialogs.RepeatInputDialog
import com.example.myclock.dialogs.RingDurationInputDialog
import com.example.myclock.dialogs.SnoozeInputDialog
import com.example.myclock.models.Alarm
import com.example.myclock.utilities.SnoozeUtils
import com.example.myclock.utilities.Utility
import com.example.myclock.viewmodels.AlarmsViewModel
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AlarmFormFragment : Fragment() {
    private val args: AlarmFormFragmentArgs by navArgs()
    private lateinit var alarm: Alarm
    private val alarmsViewModel: AlarmsViewModel by activityViewModels()
    private lateinit var fragmentView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentView = inflater.inflate(R.layout.fragment_alarm_form, container, false)
        setHasOptionsMenu(true)

        alarm = when (args.alarmId) {
            0 -> Alarm()
            else -> alarmsViewModel.alarmsLiveData.value?.get(args.alarmId - 1) ?: Alarm()
        }
        alarmsViewModel.alarmFormLiveData.observe(viewLifecycleOwner, { alarm = it })
        alarmsViewModel.alarmFormLiveData.value = alarm

        initTimeField()
        initRepeatField()
        initVibrateField()
        initLabelField()
        initRingDurationField()
        initSnoozeField()

        return fragmentView
    }

    private fun initTimeField() {
        val tvTime = fragmentView.findViewById<TextView>(R.id.tvTime)
        tvTime.text = Utility.timeToString(alarm.time)

        val llTimeField = fragmentView.findViewById<LinearLayout>(R.id.llTimeField)
        llTimeField.setOnClickListener {
            val picker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setTitleText("Set Time")
                    .setHour(alarm.time.get(Calendar.HOUR_OF_DAY))
                    .setMinute(alarm.time.get(Calendar.MINUTE))
                    .build()

            picker.addOnPositiveButtonClickListener {
                alarm.time.set(Calendar.HOUR_OF_DAY, picker.hour)
                alarm.time.set(Calendar.MINUTE, picker.minute)
                tvTime.text = Utility.timeToString(alarm.time)
            }

            picker.show(childFragmentManager, "TIME_INPUT_DIALOG")
        }
    }

    private fun initRepeatField() {
        val tvRepeat = fragmentView.findViewById<TextView>(R.id.tvRepeat)
        tvRepeat.text = Utility.getRepeatLabel(alarm.repeat)

        val llRepeatField = fragmentView.findViewById<LinearLayout>(R.id.llRepeatField)
        llRepeatField.setOnClickListener {
            val repeatInputDialog = RepeatInputDialog()
            repeatInputDialog.dialogInterface = object : DialogInterface {
                override fun cancel() {}

                override fun dismiss() {
                    tvRepeat.text = Utility.getRepeatLabel(alarm.repeat)
                }
            }

            repeatInputDialog.show(childFragmentManager, "REPEAT_INPUT_DIALOG")
        }
    }

    private fun initLabelField() {
        val tvLabel = fragmentView.findViewById<TextView>(R.id.tvLabel)
        tvLabel.text = alarm.label

        val llLabelField = fragmentView.findViewById<LinearLayout>(R.id.llLabelField)
        llLabelField.setOnClickListener {
            val labelInputDialog = LabelInputDialog()
            labelInputDialog.dialogInterface = object : DialogInterface {
                override fun cancel() {
                    TODO("Not yet implemented")
                }

                override fun dismiss() {
                    tvLabel.text = alarm.label
                }
            }

            labelInputDialog.show(childFragmentManager, "LABEL_INPUT_DIALOG")
        }
    }

    private fun initVibrateField() {
        val smVibrate = fragmentView.findViewById<SwitchMaterial>(R.id.smVibrate)
        smVibrate.isChecked = alarm.vibrate
        smVibrate.setOnCheckedChangeListener { _, isChecked ->
            alarm.vibrate = isChecked
        }

        val llVibrateField = fragmentView.findViewById<LinearLayout>(R.id.llVibrateField)
        llVibrateField.setOnClickListener {
            smVibrate.isChecked = !alarm.vibrate
        }
    }

    private fun initRingDurationField() {
        val tvRingDuration = fragmentView.findViewById<TextView>(R.id.tvRingDuration)
        tvRingDuration.text = Utility.getRingDurationLabel(alarm.ringDuration)

        val llRingDurationField = fragmentView.findViewById<LinearLayout>(R.id.llRingDurationField)
        llRingDurationField.setOnClickListener {
            val ringDurationInputDialog = RingDurationInputDialog()
            ringDurationInputDialog.dialogInterface = object : DialogInterface {
                override fun cancel() {}

                override fun dismiss() {
                    tvRingDuration.text = Utility.getRingDurationLabel(alarm.ringDuration)
                }
            }

            ringDurationInputDialog.show(childFragmentManager, "RING_DURATION_INPUT_DIALOG")
        }
    }

    private fun initSnoozeField() {
        val tvSnoozeDuration = fragmentView.findViewById<TextView>(R.id.tvSnoozeDuration)
        (SnoozeUtils.getSnoozeDurationLabel(alarm.snoozeDuration) + ", " + SnoozeUtils.getNoOfSnoozesLabel(
            alarm.noOfSnoozes
        )).also { tvSnoozeDuration.text = it }

        val llSnoozeDurationField = fragmentView.findViewById<LinearLayout>(R.id.llSnoozeDurationField)
        llSnoozeDurationField.setOnClickListener {
            val snoozeInputDialog = SnoozeInputDialog()
            snoozeInputDialog.isCancelable = false

            snoozeInputDialog.dialogInterface = object : DialogInterface {
                override fun cancel() {}

                override fun dismiss() {
                    (SnoozeUtils.getSnoozeDurationLabel(alarm.snoozeDuration) + ", " + SnoozeUtils.getNoOfSnoozesLabel(
                        alarm.noOfSnoozes
                    )).also { tvSnoozeDuration.text = it }
                }
            }

            snoozeInputDialog.show(childFragmentManager, "SNOOZE_INPUT_DIALOG")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_alarm_form, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_save -> {
            alarmsViewModel.save()
            alarmsViewModel.alarmFormLiveData.value = Alarm()
            findNavController().popBackStack()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}