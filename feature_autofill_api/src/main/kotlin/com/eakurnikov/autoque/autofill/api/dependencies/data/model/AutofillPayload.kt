package com.eakurnikov.autoque.autofill.api.dependencies.data.model

import android.content.Intent
import android.os.Bundle

/**
 * Created by eakurnikov on 2019-09-14
 */
data class AutofillPayload internal constructor(
    val type: Type,
    val clientState: Bundle
) {
    enum class Type(
        private val description: String
    ) {
        FILL("FILL"),
        UNSAFE_FILL("UNSAFE_FILL"),
        SAVE("SAVE"),
        UPDATE("UPDATE"),
        VIEW_ALL("VIEW_ALL");

        override fun toString(): String = description
    }
}

private const val CLIENT_STATE_KEY = "AUTOFILL_CLIENT_STATE"

fun Intent.getAutofillPayload(): AutofillPayload? {
    val type: AutofillPayload.Type = getType(action) ?: return null
    val clientState: Bundle = getBundleExtra(CLIENT_STATE_KEY) ?: return null
    return AutofillPayload(type, clientState)
}

fun Intent.setAutofillPayload(type: AutofillPayload.Type, clientState: Bundle): Intent {
    action = type.toString()
    putExtra(CLIENT_STATE_KEY, clientState)
    return this
}

private fun getType(description: String?): AutofillPayload.Type? {
    description ?: return null
    return try {
        AutofillPayload.Type.valueOf(description)
    } catch (e: IllegalArgumentException) {
        null
    }
}