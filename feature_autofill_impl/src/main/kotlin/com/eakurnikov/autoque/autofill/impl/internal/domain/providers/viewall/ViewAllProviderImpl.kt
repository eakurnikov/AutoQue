package com.eakurnikov.autoque.autofill.impl.internal.domain.providers.viewall

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.AutofillPayload
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.setAutofillPayload
import com.eakurnikov.autoque.autofill.impl.internal.extensions.wrapWithSender
import com.eakurnikov.autoque.autofill.impl.internal.ui.viewall.ViewAllActivity
import com.eakurnikov.common.annotations.AppContext
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-16
 */
class ViewAllProviderImpl @Inject constructor(
    @AppContext private val context: Context
) : ViewAllProvider {

    private var pendingIntentId = 0

    override fun getViewAllIntentSender(clientState: Bundle): IntentSender =
        Intent(context, ViewAllActivity::class.java)
            .setAutofillPayload(AutofillPayload.Type.VIEW_ALL, clientState)
            .wrapWithSender(context, pendingIntentId++)
}