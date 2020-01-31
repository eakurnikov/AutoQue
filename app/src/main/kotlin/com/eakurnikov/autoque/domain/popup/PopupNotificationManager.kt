package com.eakurnikov.autoque.domain.popup

import android.annotation.TargetApi
import android.app.Notification
import android.os.Build
import androidx.core.app.NotificationCompat

interface PopupNotificationManager {

    fun show(id: Int, notification: Notification)

    fun show(id: Int, builder: NotificationCompat.Builder, tag: String? = null)

    fun cancel(id: Int, tag: String? = null)

    @TargetApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(
        group: NotificationGroup,
        showBadge: Boolean,
        withSound: Boolean,
        importance: Int? = null
    )
}