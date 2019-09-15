package com.eakurnikov.autoque.autofill.api.dependencies.auth

import android.app.Activity
import com.eakurnikov.autoque.autofill.api.api.auth.AutofillAuthListener

/**
 * Created by eakurnikov on 2019-09-14
 */
interface AutofillAuthenticator {
    val activityContext: Activity
    val autofillAuthListener: AutofillAuthListener
}