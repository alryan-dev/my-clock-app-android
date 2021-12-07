package com.example.myclock.utilities

import android.content.Context
import android.util.Log
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

object Utility {
    val durations =
        arrayOf("1 minute", "5 minutes", "10 minutes", "15 minutes", "20 minutes", "30 minutes")
    val weekdays =
        arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    fun print(text: String) {
        Log.d("MY-CLOCK-APP", text)
    }

    fun showKeyboard(context: Context?, view: EditText?) {
        if (view == null) return
        view.requestFocus()

        // Not working
        // if (view.requestFocus()) {
        // val imm =
        //     context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        // imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        // }
    }

    fun timeToString(time: Calendar): String {
        return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(time.time)
    }

    fun getRepeatLabel(repeat: BooleanArray): String {
        var repeatLabel = ""
        var everyday = true
        var onlyRingOnce = true

        for (idx in repeat.indices) {
            if (!repeat[idx]) {
                if (everyday) everyday = false
                continue
            } else {
                if (onlyRingOnce) onlyRingOnce = false

                repeatLabel += when (idx) {
                    0 -> "Mon "
                    1 -> "Tue "
                    2 -> "Wed "
                    3 -> "Thu "
                    4 -> "Fri "
                    5 -> "Sat "
                    else -> "Sun "
                }
            }
        }

        return if (everyday) "Everyday" else if (onlyRingOnce) "Only ring once" else repeatLabel
    }

    fun getRingDurationLabel(ringDuration: Int): String {
        return  when (ringDuration) {
            1 -> durations[0]
            5 -> durations[1]
            10 -> durations[2]
            15 -> durations[3]
            20 -> durations[4]
            else -> durations[5]
        }
    }
}