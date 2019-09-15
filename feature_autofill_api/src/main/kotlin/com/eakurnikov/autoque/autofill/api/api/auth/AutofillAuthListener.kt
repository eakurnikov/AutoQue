package com.eakurnikov.autoque.autofill.api.api.auth

import com.eakurnikov.autoque.autofill.api.dependencies.auth.AutofillAuthPayload
import com.eakurnikov.autoque.autofill.api.dependencies.auth.AutofillAuthenticator

/**
 * Created by eakurnikov on 2019-09-14
 */
interface AutofillAuthListener {

    fun onAuth(authenticator: AutofillAuthenticator, authPayload: AutofillAuthPayload?, authResult: Boolean)
}