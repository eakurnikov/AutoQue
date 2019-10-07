package com.eakurnikov.autoque.autofill.api.dependencies.auth

import android.content.IntentSender
import android.os.Bundle

/**
 * Created by eakurnikov on 2019-09-14
 */
interface AuthProvider<T : AutofillAuthenticator> {

    val autofillAuthenticatorClass: Class<T>
    val isAuthRequired: Boolean

    fun getAuthIntentSenderForFill(clientState: Bundle): IntentSender

    fun getAuthIntentSenderForSave(clientState: Bundle): IntentSender
}