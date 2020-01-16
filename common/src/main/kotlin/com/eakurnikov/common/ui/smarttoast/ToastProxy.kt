package com.eakurnikov.common.ui.smarttoast

import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.eakurnikov.common.util.getPrivateField

/**
 * Created by eakurnikov on 2020-01-16
 */
class ToastProxy(context: Context) : BaseSmartToast(context) {

    private val toast: Toast = Toast(context)

    private var cancelOnOutsideTouch = false

    init {
        setOutsideTouchParamFlag()
    }

    override fun show(): Unit = toast.show()

    override fun cancel(): Unit = toast.cancel()

    override fun setView(view: View) {
        toast.view = view
        setOnTouchListener()
    }

    override fun setGravity(gravity: Int, xOffset: Int, yOffset: Int) {
        toast.setGravity(gravity, xOffset, yOffset)
    }

    override fun setDuration(duration: Int) {
        var duration = duration
        if (duration == Toast.LENGTH_LONG) {
            duration = Toast.LENGTH_SHORT
        }
        toast.duration = DelayConverter.convertDurationToToastConst(duration)
    }

    override fun setCancelOnOutsideTouch(cancelOnOutsideTouch: Boolean) {
        this.cancelOnOutsideTouch = cancelOnOutsideTouch
        setOnTouchListener()
    }

    private fun setOnTouchListener() {
        if (cancelOnOutsideTouch) {
            toast.view?.setOnTouchListener { v, event ->
                if (cancelOnOutsideTouch) cancel()
                false
            }
        }
    }

    private fun setOutsideTouchParamFlag() {
        toast.getPrivateField<Toast, Any?>("mTN")?.apply {
            getPrivateField<Any, WindowManager.LayoutParams?>("mParams")?.apply {
                flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH or
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            }
        }
    }
}