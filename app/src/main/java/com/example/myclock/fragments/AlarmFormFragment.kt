package com.example.myclock.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AlarmFormFragment : Fragment() {
    private val alarmsViewModel: AlarmsViewModel by activityViewModels()
    private lateinit var alarmForm: Alarm
    private lateinit var fragmentView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentView = inflater.inflate(R.layout.fragment_alarm_form, container, false)
        setHasOptionsMenu(true)

        alarmsViewModel.alarmFormLiveData.value?.let { alarmForm = it }
        alarmsViewModel.alarmFormLiveData.observe(viewLifecycleOwner, { alarmForm = it })

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
        tvTime.text = Utility.timeToString(alarmForm.time)

        val llTimeField = fragmentView.findViewById<LinearLayout>(R.id.llTimeField)
        llTimeField.setOnClickListener {
            val picker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setTitleText("Set Time")
                    .setHour(alarmForm.time.get(Calendar.HOUR_OF_DAY))
                    .setMinute(alarmForm.time.get(Calendar.MINUTE))
                    .build()

            picker.addOnPositiveButtonClickListener {
                alarmForm.time.set(Calendar.HOUR_OF_DAY, picker.hour)
                alarmForm.time.set(Calendar.MINUTE, picker.minute)
                tvTime.text = Utility.timeToString(alarmForm.time)
            }

            picker.show(childFragmentManager, "TIME_INPUT_DIALOG")
        }
    }

    private fun initRepeatField() {
        val tvRepeat = fragmentView.findViewById<TextView>(R.id.tvRepeat)
        tvRepeat.text = Utility.getRepeatLabel(alarmForm.repeat)

        val llRepeatField = fragmentView.findViewById<LinearLayout>(R.id.llRepeatField)
        llRepeatField.setOnClickListener {
            val repeatInputDialog = RepeatInputDialog()
            repeatInputDialog.dialogInterface = object : DialogInterface {
                override fun cancel() {}

                override fun dismiss() {
                    tvRepeat.text = Utility.getRepeatLabel(alarmForm.repeat)
                }
            }

            repeatInputDialog.show(childFragmentManager, "REPEAT_INPUT_DIALOG")
        }
    }

    private fun initLabelField() {
        val tvLabel = fragmentView.findViewById<TextView>(R.id.tvLabel)
        tvLabel.text = alarmForm.label

        val llLabelField = fragmentView.findViewById<LinearLayout>(R.id.llLabelField)
        llLabelField.setOnClickListener {
            val labelInputDialog = LabelInputDialog()
            labelInputDialog.dialogInterface = object : DialogInterface {
                override fun cancel() {
                    TODO("Not yet implemented")
                }

                override fun dismiss() {
                    tvLabel.text = alarmForm.label
                }
            }

            labelInputDialog.show(childFragmentManager, "LABEL_INPUT_DIALOG")
        }
    }

    private fun initVibrateField() {
        val smVibrate = fragmentView.findViewById<SwitchMaterial>(R.id.smVibrate)
        smVibrate.isChecked = alarmForm.vibrate
        smVibrate.setOnCheckedChangeListener { _, isChecked ->
            alarmForm.vibrate = isChecked
        }

        val llVibrateField = fragmentView.findViewById<LinearLayout>(R.id.llVibrateField)
        llVibrateField.setOnClickListener {
            smVibrate.isChecked = !alarmForm.vibrate
        }
    }

    private fun initRingDurationField() {
        val tvRingDuration = fragmentView.findViewById<TextView>(R.id.tvRingDuration)
        tvRingDuration.text = Utility.getRingDurationLabel(alarmForm.ringDuration)

        val llRingDurationField = fragmentView.findViewById<LinearLayout>(R.id.llRingDurationField)
        llRingDurationField.setOnClickListener {
            val ringDurationInputDialog = RingDurationInputDialog()
            ringDurationInputDialog.dialogInterface = object : DialogInterface {
                override fun cancel() {}

                override fun dismiss() {
                    tvRingDuration.text = Utility.getRingDurationLabel(alarmForm.ringDuration)
                }
            }

            ringDurationInputDialog.show(childFragmentManager, "RING_DURATION_INPUT_DIALOG")
        }
    }

    private fun initSnoozeField() {
        val tvSnoozeDuration = fragmentView.findViewById<TextView>(R.id.tvSnoozeDuration)
        (SnoozeUtils.getSnoozeDurationLabel(alarmForm.snoozeDuration) + ", " + SnoozeUtils.getNoOfSnoozesLabel(
            alarmForm.noOfSnoozes
        )).also { tvSnoozeDuration.text = it }

        val llSnoozeDurationField = fragmentView.findViewById<LinearLayout>(R.id.llSnoozeDurationField)
        llSnoozeDurationField.setOnClickListener {
            val snoozeInputDialog = SnoozeInputDialog()
            snoozeInputDialog.isCancelable = false

            snoozeInputDialog.dialogInterface = object : DialogInterface {
                override fun cancel() {}

                override fun dismiss() {
                    (SnoozeUtils.getSnoozeDurationLabel(alarmForm.snoozeDuration) + ", " + SnoozeUtils.getNoOfSnoozesLabel(
                        alarmForm.noOfSnoozes
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
            findNavController().popBackStack()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}