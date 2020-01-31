package com.eakurnikov.autoque.ui.view.popup

import android.app.Notification
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.RemoteViews
import androidx.annotation.LayoutRes
import androidx.core.app.NotificationCompat
import com.eakurnikov.autoque.R
import com.eakurnikov.autoque.domain.popup.NotificationGroup
import com.eakurnikov.autoque.domain.popup.PopupNotificationManager
import com.eakurnikov.common.annotations.AppContext
import com.eakurnikov.common.ui.SmartToastFactory
import com.eakurnikov.common.ui.smarttoast.SmartToast

abstract class Popup(
    @AppContext private val context: Context,
    private val notificationManager: PopupNotificationManager,
    @LayoutRes private val layoutToast: Int,
    @LayoutRes private val layoutNotification: Int
) {
    protected open val notificationId: Int = 100
    protected open val showLengthMs: Long = 3_500
    protected open val showDelayMs: Long = 500

    private val mUiHandler = Handler(Looper.getMainLooper())

    internal fun show() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            showAsNotification()
        } else {
            showAsToast()
        }
    }

    internal fun cancelIfNecessary() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            cancelNotification()
        }
        // toast is being canceled automatically via cancelOnOutsideTouch flag
        // so manual canceling is not necessary
    }

    private fun showAsNotification() {
        val view = RemoteViews(context.packageName, layoutNotification)
        val notification = createNotification(view)

        mUiHandler.removeCallbacksAndMessages(null)

        mUiHandler.postDelayed(
            { notificationManager.show(notificationId, notification) },
            showDelayMs
        )
        mUiHandler.postDelayed(
            { notificationManager.cancel(notificationId) },
            showDelayMs + showLengthMs
        )
    }

    private fun showAsToast() {
        val view = RemoteViews(context.packageName, layoutToast)
        val smartToast: SmartToast = createToast(view.apply(context, null))

        mUiHandler.removeCallbacksAndMessages(null)
        mUiHandler.postDelayed({ smartToast.show() }, showDelayMs)
    }

    private fun cancelNotification() {
        mUiHandler.post { notificationManager.cancel(notificationId) }
    }

    private fun createNotification(view: RemoteViews): Notification {
        notificationManager.createNotificationChannel(
            NotificationGroup.Popup,
            showBadge = false,
            withSound = false
        )

        return NotificationCompat
            .Builder(context, NotificationGroup.Popup.channelId)
            .setCustomHeadsUpContentView(view)
            .setCustomContentView(view)
            .setCustomBigContentView(view)
            .setContentTitle(context.getString(R.string.app_name))
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()
    }

    private fun createToast(view: View): SmartToast {
        return SmartToastFactory.create(
            context,
            view,
            Gravity.TOP or Gravity.FILL_HORIZONTAL,
            showLengthMs.toInt(),
            true
        )
    }
}