package com.eakurnikov.autoque.autofill.impl.domain.viewnode

import android.app.assist.AssistStructure
import android.service.autofill.SaveInfo
import android.view.View
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 *
 * Obtains view node's autofill hint, save type and web domain. Infers autofill hint using some
 * heuristics if needed.
 */
class ViewNodeHeuristics
@Inject constructor() {

    fun obtainAutofillType(viewNode: AssistStructure.ViewNode): Int? {
        return viewNode.autofillType.let { if (it == View.AUTOFILL_TYPE_TEXT) it else null }
    }

    fun obtainAutofillHint(viewNode: AssistStructure.ViewNode): String? {
        with(viewNode) {
            /**
             * First try the explicit autofill hints.
             */
            autofillHints?.let { autofillHints: Array<String> ->
                if (!autofillHints.isNullOrEmpty()) {
                    /**
                     * We're simple, we only care about the first hint.
                     * TODO: implement saving a list of autofill hints.
                     */
                    return autofillHints[0].validateAutofillHint()
                }
            }

            /**
             * Then try some rudimentary heuristics based on other node properties.
             */
            inferAutofillHint(hint)?.let { return it }
            inferAutofillHint(idEntry)?.let { return it }

            text ?: return null
            className ?: return null

            if (className.contains("EditText")) {
                inferAutofillHint(text.toString())?.let { return it }
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

    private fun inferAutofillHint(fieldDescription: String?): String? {
        val text: String = fieldDescription?.toLowerCase() ?: return null

        return when {
            text.shouldBeIgnored() -> null
            text.isPassword() -> View.AUTOFILL_HINT_PASSWORD
            text.isLogin() -> View.AUTOFILL_HINT_USERNAME
            text.isEmail() -> View.AUTOFILL_HINT_EMAIL_ADDRESS
            text.isPhone() -> View.AUTOFILL_HINT_PHONE
            else -> null
        }
    }

    private fun String.shouldBeIgnored(): Boolean {
        return when {
            contains("label") -> true
            contains("container") -> true
            else -> false
        }
    }

    private fun String.isLogin(): Boolean {
        return when {
            contains("username") -> true
            contains("login") -> true
            contains("nickname") -> true
            contains("id") -> true
            contains("логин") -> true
            contains("пользователя") -> true
            contains("псевдоним") -> true
            else -> false
        }
    }

    private fun String.isPassword(): Boolean {
        return when {
            contains("password") -> true
            contains("пароль") -> true
            else -> false
        }
    }

    private fun String.isEmail(): Boolean {
        return when {
            contains("email") -> true
            contains("электрон") && (contains("почт") || contains("адрес")) -> true
            else -> false
        }
    }

    private fun String.isPhone(): Boolean {
        return when {
            contains("phone") -> true
            contains("телефон") -> true
            else -> false
        }
    }

    private fun String.validateAutofillHint(): String? {
        return when (this) {
            View.AUTOFILL_HINT_USERNAME -> this
            View.AUTOFILL_HINT_PASSWORD -> this
            View.AUTOFILL_HINT_EMAIL_ADDRESS -> this
            View.AUTOFILL_HINT_PHONE -> this
            else -> null
        }
    }
}