package com.eakurnikov.common.ui.smarttoast

import android.content.Context
import android.view.View

/**
 * Created by eakurnikov on 2020-01-16
 */
abstract class BaseSmartToast(protected val context: Context) : SmartToast {

    abstract fun setView(view: View)

    abstract fun setGravity(gravity: Int, xOffset: Int, yOffset: Int)

    abstract fun setDuration(duration: Int)

    abstract fun setCancelOnOutsideTouch(cancelOnOutsideTouch: Boolean)
}