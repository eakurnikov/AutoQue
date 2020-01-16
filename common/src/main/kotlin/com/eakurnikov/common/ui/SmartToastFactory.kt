package com.eakurnikov.common.ui

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.BadTokenException
import android.widget.Toast
import com.eakurnikov.common.ui.smarttoast.AutoQueToast
import com.eakurnikov.common.ui.smarttoast.BaseSmartToast
import com.eakurnikov.common.ui.smarttoast.SmartToast
import com.eakurnikov.common.ui.smarttoast.ToastProxy

/**
 * Created by eakurnikov on 2020-01-16
 */
object SmartToastFactory {

    /**
     * Some devices, such as XIaomi Mi4, doesn't not support 'AutoQue Toast' and throw
     * WindowManager.BadTokenException when we we add view to WindowManager with type 'TYPE_TOAST'.
     */
    private var isAutoQueToastSupported: Boolean? = null

    /**
     * Create smart toast.
     *
     * @param context application context
     * @param layout  view that will be displayed
     */
    fun create(context: Context, layout: View): SmartToast {
        return create(context, layout, Gravity.BOTTOM, Toast.LENGTH_LONG, false)
    }

    /**
     * Create smart toast.
     *
     * @param context application context
     * @param layout view that will be displayed
     * @param gravity location at which the notification should appear on the screen.
     * @param duration duration of which will be displayed toast, allowed as constants:
     * Toast.LENGTH_SHORT, Toast.LENGTH_LONG and specify the time in milliseconds.
     */
    fun create(
        context: Context,
        layout: View,
        gravity: Int,
        duration: Int
    ): SmartToast = create(context, layout, gravity, duration, false)

    /**
     * Create smart toast.
     *
     * @param context application context
     * @param layout view that will be displayed
     * @param gravity location at which the notification should appear on the screen.
     * @param duration duration of which will be displayed toast, allowed as constants:
     * Toast.LENGTH_SHORT, Toast.LENGTH_LONG and specify the time in milliseconds.
     * @param cancelOnOutsideTouch if true, toast will be auto canceled on outside touch.
     * This option may not be supported on some devices or Android versions.
     * @return
     */
    fun create(
        context: Context,
        layout: View,
        gravity: Int,
        duration: Int,
        cancelOnOutsideTouch: Boolean
    ): SmartToast = create(context).apply {
        setGravity(gravity, 0, 0)
        setDuration(duration)
        setView(layout)
        setCancelOnOutsideTouch(cancelOnOutsideTouch)
    }

    private fun create(context: Context): BaseSmartToast =
        if (isSupportedDevice(context)) AutoQueToast(context) else ToastProxy(context)

    private fun isSupportedDevice(context: Context): Boolean {
        return isAutoQueToastSupported ?: run {
            isAutoQueToastSupported = isToastSupported(context)
            isAutoQueToastSupported!!
        }
    }

    private fun isToastSupported(context: Context): Boolean {
        val params: WindowManager.LayoutParams =
            WindowManager.LayoutParams().apply {
                x = -1
                y = -1
                height = 1
                width = 1
                format = PixelFormat.TRANSPARENT
                type = WindowManager.LayoutParams.TYPE_TOAST
                flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH or
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            }

        val view = View(context)

        val windowManager: WindowManager =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        var supported = true

        try {
            windowManager.addView(view, params)
            windowManager.removeView(view)
        } catch (e: BadTokenException) {
            supported = false
        }

        return supported
    }
}