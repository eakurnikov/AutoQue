package com.eakurnikov.autoque.domain.autofill.auth

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.AutofillPayload
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.setAutofillPayload
import com.eakurnikov.autoque.autofill.api.dependencies.domain.auth.AutofillAuthProvider
import com.eakurnikov.autoque.domain.autofill.auth.AutoQueAuthentication.isSessionExpired
import com.eakurnikov.autoque.ui.auth.AuthActivity
import com.eakurnikov.common.annotations.AppContext
import com.eakurnikov.common.di.annotations.AppScope
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 */
@AppScope
class AutofillAuthProviderImpl @Inject constructor(
    @AppContext private val context: Context
) : AutofillAuthProvider<AuthActivity> {

    private var pendingIntentId = 0

    override val autofillAuthUiClass: Class<AuthActivity> = AuthActivity::class.java

    override val isAuthRequired: Boolean
        get() = isSessionExpired

    override fun getFillAuthIntentSender(clientState: Bundle): IntentSender {
        return Intent(context, autofillAuthUiClass)
            .setAutofillPayload(AutofillPayload.Type.FILL, clientState)
            .wrapWithSender(context)
    }

    override fun getUnsafeFillAuthIntentSender(clientState: Bundle): IntentSender {
        return Intent(context, autofillAuthUiClass)
            .setAutofillPayload(AutofillPayload.Type.UNSAFE_FILL, clientState)
            .wrapWithSender(context)
    }

    override fun getSaveAuthIntentSender(clientState: Bundle): IntentSender {
        return Intent(context, autofillAuthUiClass)
            .addFlags(
                Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS or
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            )
            .setAutofillPayload(AutofillPayload.Type.SAVE, clientState)
            .wrapWithSender(context)
    }

    override fun getUpdateAuthIntentSender(clientState: Bundle): IntentSender {
        return Intent(context, autofillAuthUiClass)
            .addFlags(
                Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS or
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            )
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