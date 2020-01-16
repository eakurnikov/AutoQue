package com.eakurnikov.autoque.autofill.api.api

import com.eakurnikov.autoque.autofill.api.api.domain.enable.AutofillServiceEnabler
import com.eakurnikov.autoque.autofill.api.api.domain.select.AutofillServiceSelector
import com.eakurnikov.autoque.autofill.api.api.presentation.auth.AutofillAuthPresenter
import com.eakurnikov.autoque.autofill.api.api.presentation.disclaimer.AutofillDisclaimerPresenter
import com.eakurnikov.autoque.autofill.api.api.presentation.update.AutofillUpdatePromptPresenter

/**
 * Created by eakurnikov on 2019-09-14
 */
interface AutofillFeatureApi {

    val autofillServiceEnabler: AutofillServiceEnabler

    val autofillServiceSelector: AutofillServiceSelector

    val autofillAuthPresenter: AutofillAuthPresenter

    val autofillDisclaimerPresenter: AutofillDisclaimerPresenter

    val autofillUpdatePromptPresenter: AutofillUpdatePromptPresenter
}