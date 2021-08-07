package com.example.myclock.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.myclock.R
import com.example.myclock.utilities.Utility
import com.example.myclock.viewmodels.AlarmsViewModel

class LabelInputDialog : DialogFragment() {
    private val alarmsViewModel: AlarmsViewModel by activityViewModels()
    lateinit var dialogInterface: DialogInterface

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alarm = alarmsViewModel.alarmFormLiveData.value
        val view = layoutInflater.inflate(R.layout.dialog_field_input, null)
        val etLabel = view.findViewById<EditText>(R.id.etLabel)
        etLabel.setText(alarm?.label)

        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setTitle("Label")
                .setView(view)
                .setPositiveButton("OK") { _, _ ->
                    alarm?.label = etLabel.text.toString()
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
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        Utility.showKeyboard(context, dialog?.findViewById(R.id.etLabel))
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dialogInterface.dismiss()
    }
}