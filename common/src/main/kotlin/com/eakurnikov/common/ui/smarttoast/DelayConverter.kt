package com.eakurnikov.common.ui.smarttoast

import android.widget.Toast

/**
 * Created by eakurnikov on 2020-01-16
 */
object DelayConverter {
    const val DEFAULT_LONG_DELAY: Int = 3500
    const val DEFAULT_SHORT_DELAY: Int = 2000

    fun convertDurationToMs(duration: Int): Int {
        return when (duration) {
            Toast.LENGTH_SHORT -> DEFAULT_SHORT_DELAY
            Toast.LENGTH_LONG -> DEFAULT_LONG_DELAY
            else -> duration
        }
    }

    fun convertDurationToToastConst(duration: Int): Int {
        return when {
            duration == Toast.LENGTH_SHORT || duration == Toast.LENGTH_LONG -> duration
            duration >= DEFAULT_LONG_DELAY -> Toast.LENGTH_LONG
            else -> Toast.LENGTH_SHORT
        }
    }
}