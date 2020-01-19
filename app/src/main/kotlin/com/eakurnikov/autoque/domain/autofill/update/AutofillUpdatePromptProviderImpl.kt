package com.eakurnikov.autoque.domain.autofill.update

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.AutofillPayload
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.setAutofillPayload
import com.eakurnikov.autoque.autofill.api.dependencies.domain.update.AutofillUpdatePromptProvider
import com.eakurnikov.autoque.view.autofill.AutofillUpdatePromptActivity
import com.eakurnikov.common.annotations.AppContext
import com.eakurnikov.common.di.annotations.AppScope
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-16
 */
@AppScope
class AutofillUpdatePromptProviderImpl @Inject constructor(
    @AppContext private val context: Context
) : AutofillUpdatePromptProvider<AutofillUpdatePromptActivity> {

    private var pendingIntentId = 0

    override val autofillUpdatePromptUiClass: Class<AutofillUpdatePromptActivity> =
        AutofillUpdatePromptActivity::class.java

    override fun getPromptIntentSender(clientState: Bundle): IntentSender {
        return Intent(context, autofillUpdatePromptUiClass)
            .addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS or Intent.FLAG_ACTIVITY_NEW_TASK)
            .setAutofillPayload(AutofillPayload.Type.UPDATE, clientState)
            .wrapWithSender(context)
    }

    private fun Intent.wrapWithSender(context: Context): IntentSender {
        val pendingAuthIntent: PendingIntent =
            PendingIntent.getActivity(
                context,
                ++pendingIntentId,
                this,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
        return pendingAuthIntent.intentSender
    }
}