package com.example.myclock.dialogfragments

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.myclock.R
import com.example.myclock.Utility
import com.example.myclock.interfaces.DialogButtonsListener

class LabelInputDialog : DialogFragment() {
    private lateinit var dialogButtonsListener: DialogButtonsListener

    fun setDialogButtonsListener(dialogButtonsListener: DialogButtonsListener) {
        this.dialogButtonsListener = dialogButtonsListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val view = layoutInflater.inflate(R.layout.dialog_field_input, null)

            builder.setTitle("Label")
                .setView(view)
                .setPositiveButton("OK") { dialog, id ->
                    val label = view.findViewById<EditText>(R.id.etLabel).text.toString()
                    dialogButtonsListener.onDialogPositiveClick(label)
                }
                .setNegativeButton("CANCEL") { dialog, id -> }

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
}