package com.example.p006_activitynavigation.activity.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Options(
    val boxCount: Int,
    val isTimerEnabled: Boolean
) : Parcelable {

    companion object {
        @JvmStatic val DEFAULT = Options(boxCount = 3, isTimerEnabled = false)
    }
}