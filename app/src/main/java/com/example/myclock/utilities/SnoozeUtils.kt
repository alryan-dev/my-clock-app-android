package com.example.myclock.utilities

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.InverseMethod
import com.example.myclock.models.Alarm
import com.google.android.material.slider.Slider

object SnoozeUtils {
    fun getSnoozeDurationSliderValue(snoozeDuration: Int?): Float {
        return when (snoozeDuration) {
            5 -> 0f
            10 -> 5f
            15 -> 10f
            20 -> 15f
            25 -> 20f
            else -> 25f
        }
    }

    fun getSnoozeDurationValue(sliderValue: Float?): Int {
        return when (sliderValue) {
            0f -> 5
            5f -> 10
            10f -> 15
            15f -> 20
            20f -> 25
            else -> 30
        }
    }

    fun getNoOfSnoozesSliderValue(noOfSnoozes: Int?): Float {
        return when (noOfSnoozes) {
            1 -> 0f
            3 -> 4f
            5 -> 8f
            else -> 12f
        }
    }

    fun getNoOfSnoozesValue(sliderValue: Float?): Int {
        return when (sliderValue) {
            0f -> 1
            4f -> 3
            8f -> 5
            else -> 10
        }
    }

    fun getSnoozeDurationLabel(snoozeDuration: Int): String {
        return when (snoozeDuration) {
            5 -> "5 minutes"
            10 -> "10 minutes"
            15 -> "15 minutes"
            20 -> "20 minutes"
            25 -> "25 minutes"
            else -> "30 minutes"
        }
    }

    fun getNoOfSnoozesLabel(snoozeDuration: Int): String {
        return when (snoozeDuration) {
            1 -> "1x"
            3 -> "3x"
            5 -> "5x"
            else -> "10x"
        }
    }

    fun getSnoozeLabel(alarm: Alarm) : String =
        getSnoozeDurationLabel(alarm.snoozeDuration) + ", " + getNoOfSnoozesLabel(
            alarm.noOfSnoozes
        )
}

@InverseBindingAdapter(attribute = "android:value")
fun getSliderValue(slider: Slider) = slider.value

@BindingAdapter("android:valueAttrChanged")
fun setSliderListeners(slider: Slider, attrChange: InverseBindingListener) {
    slider.addOnChangeListener { _, _, _ ->
        attrChange.onChange()
    }
}