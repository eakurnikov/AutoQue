package com.eakurnikov.autoque.autofill.api.dependencies.auth

import android.content.Intent
import android.os.Bundle

/**
 * Created by eakurnikov on 2019-09-14
 */
data class AutofillAuthPayload internal constructor(
    val authType: AutofillAuthType,
    val clientState: Bundle
)

private const val CLIENT_STATE_KEY = "AUTOFILL_CLIENT_STATE"

fun Intent.getAutofillAuthPayload(): AutofillAuthPayload? {
    val authType: AutofillAuthType = getAuthType(action) ?: return null
    val clientState: Bundle = getBundleExtra(CLIENT_STATE_KEY) ?: return null

    return AutofillAuthPayload(authType, clientState)
}

fun Intent.setAutofillAuthPayload(authType: AutofillAuthType, clientState: Bundle): Intent {
    action = authType.toString()
    putExtra(CLIENT_STATE_KEY, clientState)
    return this
}

private fun getAuthType(description: String?): AutofillAuthType? {
    description ?: return null
    return try {
        AutofillAuthType.valueOf(description)
    } catch (e: IllegalArgumentException) {
        null
    }
}