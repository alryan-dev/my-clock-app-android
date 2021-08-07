package com.example.myclock.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity
class Alarm(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
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