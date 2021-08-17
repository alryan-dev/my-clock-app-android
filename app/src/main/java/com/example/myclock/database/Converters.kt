package com.example.myclock.database

import androidx.room.TypeConverter
import com.example.myclock.utilities.Utility
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    @TypeConverter
    fun stringToDateTime(value: String?): Calendar? {
        val sdf = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        return value?.let {
            val calendar = Calendar.getInstance()
            sdf.parse(it)?.let { date ->
                calendar.time = date
                calendar
            }
        }
    }

    @TypeConverter
    fun dateTimeToString(calendar: Calendar?): String {
        return calendar?.let {
            Utility.timeToString(it)
        } ?: ""
    }
    @TypeConverter
    fun stringToBooleanArray(value: String?): BooleanArray? {
        return value?.let {
            Gson().fromJson(it, BooleanArray::class.java)
        }
    }

    @TypeConverter
    fun booleanArrayToString(booleanArray: BooleanArray?): String {
        return booleanArray?.let {
            Gson().toJson(it)
        } ?: ""
    }
}