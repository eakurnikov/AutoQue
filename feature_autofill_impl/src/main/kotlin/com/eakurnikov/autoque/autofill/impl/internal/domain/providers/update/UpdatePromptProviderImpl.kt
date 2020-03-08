package com.eakurnikov.autoque.autofill.impl.internal.domain.providers.update

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.AutofillPayload
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.setAutofillPayload
import com.eakurnikov.autoque.autofill.impl.internal.extensions.wrapWithSender
import com.eakurnikov.autoque.autofill.impl.internal.ui.update.UpdatePromptActivity
import com.eakurnikov.common.annotations.AppContext
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-16
 */
class UpdatePromptProviderImpl @Inject constructor(
    @AppContext private val context: Context
) : UpdatePromptProvider {

    private var pendingIntentId = 0

    override fun getPromptIntentSender(clientState: Bundle): IntentSender {
        return Intent(context, UpdatePromptActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS or Intent.FLAG_ACTIVITY_NEW_TASK)
            .setAutofillPayload(AutofillPayload.Type.UPDATE, clientState)
            .wrapWithSender(context, pendingIntentId)
    }
}