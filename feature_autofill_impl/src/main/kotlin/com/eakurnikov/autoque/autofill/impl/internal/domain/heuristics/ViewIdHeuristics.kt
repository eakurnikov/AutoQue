package com.eakurnikov.autoque.autofill.impl.internal.domain.heuristics

import android.annotation.TargetApi
import android.os.Build
import android.view.View
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-09
 */
@TargetApi(Build.VERSION_CODES.O)
class ViewIdHeuristics @Inject constructor() {

    fun inferAutofillHint(idEntry: String?): String? {
        val id: String = idEntry?.toLowerCase() ?: return null
        return when {
            id.shouldBeIgnored() -> null
            id.isPassword() -> View.AUTOFILL_HINT_PASSWORD
            id.isLogin() -> View.AUTOFILL_HINT_USERNAME
            id.isEmail() -> View.AUTOFILL_HINT_EMAIL_ADDRESS
            id.isPhone() -> View.AUTOFILL_HINT_PHONE
            else -> null
        }
    }

    private fun String.shouldBeIgnored(): Boolean {
        return when {
            contains("root") -> true
            contains("bar") -> true
            contains("label") -> true
            contains("textview") -> true
            contains("image") -> true
            contains("icon") -> true
            contains("btn") -> true
            contains("button") -> true
            contains("container") -> true
            contains("content") -> true
            contains("wrapper") -> true
            contains("layout") -> true
            contains("search") -> true
            contains("message") -> true
            contains("comment") -> true
            contains("descr") -> true
            else -> false
        }
    }

    private fun String.isLogin(): Boolean {
        return when {
            contains("login") -> true
            contains("id") -> true
            contains("username") -> true
            contains("nickname") -> true
            else -> false
        }
    }

    private fun String.isPassword(): Boolean = contains("pass")
    private fun String.isEmail(): Boolean = contains("email")
    private fun String.isPhone(): Boolean = contains("phone")
}