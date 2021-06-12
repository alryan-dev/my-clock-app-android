package com.example.myclock.models

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class Alarm(
    var id: Int = 0,
    var time: Calendar = Calendar.getInstance(),
    var vibrate: Boolean = false,
    var label: String = "Alarm",
    var enabled: Boolean = false,
    var repeat: BooleanArray = booleanArrayOf(
        false,
        false,
        false,
        false,
        false,
        false,
        false
    ),
    var ringDuration: Int = 5,
    var snoozeDuration: Int = 10,
    var noOfSnoozes: Int = 3
) : Parcelable {
    companion object {
        fun copy(alarm: Alarm?): Alarm {
            val json = Gson().toJson(alarm)
            return Gson().fromJson(json, Alarm::class.java)
        }
    }
}