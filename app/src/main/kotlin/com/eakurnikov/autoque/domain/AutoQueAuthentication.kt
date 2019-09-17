package com.eakurnikov.autoque.domain

import android.content.Context
import android.os.Handler

/**
 * Created by eakurnikov on 2019-09-15
 */
object AutoQueAuthentication {

    const val duration: Long = 15_000

    var isSessionExpired: Boolean = true
        private set

    fun authenticate(context: Context, sessionDuration: Long) {
        isSessionExpired = false
        Handler(context.mainLooper).postDelayed({ isSessionExpired = true }, sessionDuration)
    }
}