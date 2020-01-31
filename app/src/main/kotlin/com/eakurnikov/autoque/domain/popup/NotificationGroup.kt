package com.eakurnikov.autoque.domain.popup

import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import com.eakurnikov.autoque.R

enum class NotificationGroup(
    val id: Int,
    val channelId: String
) {
    Regular(0, "REGULAR"),
    Popup(1, "POPUP");

    val soundUri: Uri?
        get() = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

    fun getChannelName(context: Context): String {
        return when (this) {
            Regular -> context.getString(R.string.regular_notification_channel_name)
            Popup -> context.getString(R.string.popup_notification_channel_name)
        }
    }

    fun getChannelDescription(context: Context): String? {
        return when (this) {
            Regular -> context.getString(R.string.popup_notification_channel_description)
            Popup -> null
        }
    }
}