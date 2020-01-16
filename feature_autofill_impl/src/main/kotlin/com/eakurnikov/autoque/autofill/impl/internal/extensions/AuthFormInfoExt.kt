package com.eakurnikov.autoque.autofill.impl.internal.extensions

import android.view.autofill.AutofillId
import com.eakurnikov.autoque.autofill.impl.internal.data.model.AuthFormInfo

/**
 * Created by eakurnikov on 2019-09-15
 */
val AuthFormInfo.autofillIdsAsArray: Array<AutofillId>
    get() = when {
        login == null -> arrayOf(password!!.autofillId)
        password == null -> arrayOf(login.autofillId)
        else -> arrayOf(login.autofillId, password.autofillId)
    }

val AuthFormInfo.compositeSaveType: Int
    get() = when {
        login == null -> password!!.saveType
        password == null -> login.saveType
        else -> login.saveType or password.saveType
    }