package com.eakurnikov.autoque.autofill.impl.internal.domain.heuristics

import android.annotation.TargetApi
import android.app.assist.AssistStructure
import android.os.Build
import android.service.autofill.SaveInfo
import android.view.View
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-09
 *
 * Obtains view node's autofill hint, save type and web domain. Infers autofill hint using some
 * heuristics if needed.
 */
@TargetApi(Build.VERSION_CODES.O)
class ViewNodeHeuristics @Inject constructor(
    private val textHeuristics: TextHeuristics,
    private val viewIdHeuristics: ViewIdHeuristics
) {
    fun obtainAutofillType(viewNode: AssistStructure.ViewNode): Int? {
        return viewNode.autofillType.let { if (it == View.AUTOFILL_TYPE_TEXT) it else null }
    }

    fun obtainAutofillHint(viewNode: AssistStructure.ViewNode): String? {
        with(viewNode) {
            /**
             * First try the explicit autofill hints.
             */
            autofillHints?.let { autofillHints: Array<String> ->
                if (autofillHints.isNotEmpty()) {
                    return autofillHints.find { it.isAutofillHintSupported() }
                }
            }

            /**
             * Then try some rudimentary heuristics based on other node properties.
             */
            textHeuristics.inferAutofillHint(hint)?.let { return it }
            viewIdHeuristics.inferAutofillHint(idEntry)?.let { return it }

            if (text != null && className != null && className.contains("EditText")) {
                textHeuristics.inferAutofillHint(text.toString())?.let { return it }
            }
        }

        return null
    }

    fun obtainAutofillValue(viewNode: AssistStructure.ViewNode): String? {
        return viewNode.autofillValue?.let { if (it.isText) it.textValue.toString() else null }
    }

    fun obtainSaveType(autofillHint: String): Int {
        return when (autofillHint) {
            View.AUTOFILL_HINT_USERNAME -> SaveInfo.SAVE_DATA_TYPE_USERNAME
            View.AUTOFILL_HINT_PASSWORD -> SaveInfo.SAVE_DATA_TYPE_PASSWORD
            View.AUTOFILL_HINT_EMAIL_ADDRESS -> SaveInfo.SAVE_DATA_TYPE_EMAIL_ADDRESS
            View.AUTOFILL_HINT_PHONE -> SaveInfo.SAVE_DATA_TYPE_USERNAME
            else -> SaveInfo.SAVE_DATA_TYPE_GENERIC
        }
    }

    private fun String?.isAutofillHintSupported(): Boolean {
        return when (this) {
            View.AUTOFILL_HINT_USERNAME -> true
            View.AUTOFILL_HINT_PASSWORD -> true
            View.AUTOFILL_HINT_EMAIL_ADDRESS -> true
            View.AUTOFILL_HINT_PHONE -> true
            else -> false
        }
    }
}