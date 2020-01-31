package com.eakurnikov.autoque.domain.autofill.disclaimer

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.AutofillPayload
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.setAutofillPayload
import com.eakurnikov.autoque.autofill.api.dependencies.domain.disclaimer.AutofillDisclaimerProvider
import com.eakurnikov.autoque.ui.autofill.AutofillDisclaimerActivity
import com.eakurnikov.common.annotations.AppContext
import com.eakurnikov.common.di.annotations.AppScope
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-16
 */
@AppScope
class AutofillDisclaimerProviderImpl @Inject constructor(
    @AppContext private val context: Context
) : AutofillDisclaimerProvider<AutofillDisclaimerActivity> {

    private var pendingIntentId = 0

    override val autofillDisclaimerUiClass: Class<AutofillDisclaimerActivity> =
        AutofillDisclaimerActivity::class.java

    override fun getDisclaimerIntentSender(clientState: Bundle): IntentSender {
        return Intent(context, autofillDisclaimerUiClass)
            .setAutofillPayload(AutofillPayload.Type.UNSAFE_FILL, clientState)
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