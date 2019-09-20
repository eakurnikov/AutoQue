package com.eakurnikov.autoque.dependencies.auth

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import com.eakurnikov.autoque.autofill.api.dependencies.auth.AutofillAuthProvider
import com.eakurnikov.autoque.autofill.api.dependencies.auth.AutofillAuthType
import com.eakurnikov.autoque.autofill.api.dependencies.auth.setAutofillAuthPayload
import com.eakurnikov.autoque.domain.AutoQueAuthentication.isSessionExpired
import com.eakurnikov.autoque.view.AuthActivity
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

    override val autofillAuthenticatorClass: Class<AuthActivity> = AuthActivity::class.java

    override val isAuthRequired: Boolean
        get() = isSessionExpired

    override fun getAuthIntentSenderForFill(clientState: Bundle): IntentSender {
        return Intent(context, autofillAuthenticatorClass)
            .setAutofillAuthPayload(AutofillAuthType.FILL, clientState)
            .wrapWithSender(context)
    }

    override fun getAuthIntentSenderForSave(clientState: Bundle): IntentSender {
        return Intent(context, autofillAuthenticatorClass)
            .addFlags(
                Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS or
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            )
            .setAutofillAuthPayload(AutofillAuthType.SAVE, clientState)
            .wrapWithSender(context)
    }

    private fun Intent.wrapWithSender(context: Context): IntentSender {
        val pendingAuthIntent: PendingIntent = PendingIntent.getActivity(
            context,
            ++pendingIntentId,
            this,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        return pendingAuthIntent.intentSender
    }
}