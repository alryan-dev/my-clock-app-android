package com.example.myclock.database

import androidx.room.TypeConverter
import com.example.myclock.utilities.Utility
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Calendar? {
        return value?.let {
            Calendar.getInstance().apply {
                timeInMillis = value
            }
        }
    }

    @TypeConverter
    fun dateToTimestamp(calendar: Calendar?): Long? {
        return calendar?.timeInMillis
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