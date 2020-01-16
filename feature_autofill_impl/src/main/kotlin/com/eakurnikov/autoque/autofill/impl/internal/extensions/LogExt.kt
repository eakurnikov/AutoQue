package com.eakurnikov.autoque.autofill.impl.internal.extensions

import android.util.Log

/**
 * Created by eakurnikov on 2020-01-16
 */
fun log(message: String, error: Throwable? = null) {
    Log.i("AUTOFILL", message)
    error?.printStackTrace()
}