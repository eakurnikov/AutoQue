package com.eakurnikov.autoque.autofill.impl.internal.domain.heuristics

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.view.View
import androidx.annotation.ArrayRes
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.common.annotations.AppContext
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-09
 */
@TargetApi(Build.VERSION_CODES.O)
class TextHeuristics @Inject constructor(
    @AppContext private val context: Context
) {
    fun inferAutofillHint(fieldDescription: String?): String? {
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

    private fun String.shouldBeIgnored(): Boolean = containsSomeFrom(R.array.faf_autofill_ignore_list)
    private fun String.isLogin(): Boolean = containsSomeFrom(R.array.faf_login_synonyms)
    private fun String.isPassword(): Boolean = containsSomeFrom(R.array.faf_password_synonyms)
    private fun String.isEmail(): Boolean = containsSomeFrom(R.array.faf_email_synonyms)
    private fun String.isPhone(): Boolean = containsSomeFrom(R.array.faf_phone_synonyms)

    private fun String.containsSomeFrom(@ArrayRes arrayRes: Int): Boolean {
        context.resources.getStringArray(arrayRes).forEach { if (contains(it)) return true }
        return false
    }
}