package com.eakurnikov.autoque.autofill.impl.internal.domain.providers.disclaimer

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.AutofillPayload
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.setAutofillPayload
import com.eakurnikov.autoque.autofill.impl.internal.extensions.wrapWithSender
import com.eakurnikov.autoque.autofill.impl.internal.ui.disclaimer.DisclaimerActivity
import com.eakurnikov.common.annotations.AppContext
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-16
 */
class DisclaimerProviderImpl @Inject constructor(
    @AppContext private val context: Context
) : DisclaimerProvider {

    private var pendingIntentId = 0

    override fun getDisclaimerIntentSender(clientState: Bundle): IntentSender {
        return Intent(context, DisclaimerActivity::class.java)
            .setAutofillPayload(AutofillPayload.Type.UNSAFE_FILL, clientState)
            .wrapWithSender(context, pendingIntentId++)
    }
}