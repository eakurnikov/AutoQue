package com.eakurnikov.autoque.domain.popup

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_HIGH
import com.eakurnikov.common.annotations.AppContext
import com.eakurnikov.common.di.annotations.AppScope
import javax.inject.Inject

@AppScope
class PopupNotificationManagerImpl @Inject constructor(
    @AppContext private val context: Context
) : PopupNotificationManager {

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun show(id: Int, notification: Notification) {
        notificationManager.notify(id, notification)
    }

    override fun show(id: Int, builder: NotificationCompat.Builder, tag: String?) {
        tag
            ?.let { notificationManager.notify(it, id, builder.build()) }
            ?: notificationManager.notify(id, builder.build())

    }

    override fun cancel(id: Int, tag: String?) {
        tag
            ?.let { notificationManager.cancel(it, id) }
            ?: notificationManager.cancel(id)

    }

    override fun createNotificationChannel(
        group: NotificationGroup,
        showBadge: Boolean,
        withSound: Boolean,
        importance: Int?
    ) {
        NotificationChannel(
            group.channelId,
            group.getChannelName(context),
            importance ?: IMPORTANCE_HIGH
        ).apply {
            setShowBadge(showBadge)
        }.apply {
            if (!withSound) setSound(null, null)
        }.apply {
            group.getChannelDescription(context)?.let { description = it }
        }.also {
            notificationManager.createNotificationChannel(it)
        }
    }
}