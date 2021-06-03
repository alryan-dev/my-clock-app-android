package com.example.myclock.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class Alarm(
    var id: Int = 0,
    var time: Date = Date(),
    var vibrate: Boolean = false,
    var label: String = "",
    var enabled: Boolean = false,
) : Parcelable {}