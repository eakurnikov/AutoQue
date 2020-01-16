package com.eakurnikov.autoque.autofill.api.api.presentation.disclaimer

import com.eakurnikov.autoque.autofill.api.dependencies.data.model.AutofillPayload
import com.eakurnikov.autoque.autofill.api.dependencies.ui.disclaimer.AutofillDisclaimerUi

/**
 * Created by eakurnikov on 2020-01-16
 */
interface AutofillDisclaimerPresenter {

    fun onDisclaimerResponse(
        disclaimerUi: AutofillDisclaimerUi,
        disclaimerPayload: AutofillPayload?,
        allowAutofill: Boolean
    )
}