package com.eakurnikov.autoque.autofill.api.api.presentation.auth

import com.eakurnikov.autoque.autofill.api.dependencies.data.model.AutofillPayload
import com.eakurnikov.autoque.autofill.api.dependencies.ui.auth.AutofillAuthUi

/**
 * Created by eakurnikov on 2020-01-16
 */
interface AutofillAuthPresenter {

    fun onAuthenticate(
        authUi: AutofillAuthUi,
        authPayload: AutofillPayload?,
        authResult: Boolean
    )
}