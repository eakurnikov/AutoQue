package com.eakurnikov.autoque.autofill.impl.internal.extensions

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentSender

/**
 * Created by eakurnikov on 2019-09-14
 */

fun Intent.wrapWithSender(context: Context, pendingIntentId: Int): IntentSender =
        PendingIntent.getActivity(
                context,
                pendingIntentId,
                this,
                PendingIntent.FLAG_CANCEL_CURRENT
        ).intentSender