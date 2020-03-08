package com.eakurnikov.autoque.autofill.api.dependencies.domain.auth

import android.content.IntentSender
import android.os.Bundle
import com.eakurnikov.autoque.autofill.api.dependencies.ui.auth.AutofillAuthUi

/**
 * Created by eakurnikov on 2020-01-16
 */
interface AutofillAuthProvider<T : AutofillAuthUi> {

    val isAuthRequired: Boolean

    val autofillAuthUiClass: Class<T>

    fun getFillAuthIntentSender(clientState: Bundle): IntentSender

    fun getUnsafeFillAuthIntentSender(clientState: Bundle): IntentSender

    fun getSaveAuthIntentSender(clientState: Bundle): IntentSender

    fun getUpdateAuthIntentSender(clientState: Bundle): IntentSender

    fun getViewAllAuthIntentSender(clientState: Bundle): IntentSender
}