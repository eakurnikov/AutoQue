package com.eakurnikov.autoque.autofill.api.api

import com.eakurnikov.autoque.autofill.api.api.auth.AutofillAuthListener
import com.eakurnikov.autoque.autofill.api.api.registrar.AutofillServiceRegistrar
import com.eakurnikov.autoque.autofill.api.api.selector.AutofillServiceSelector

/**
 * Created by eakurnikov on 2019-09-14
 */
interface AutofillFeatureApi {
    val autofillServiceRegistrar: AutofillServiceRegistrar
    val autofillServiceSelector: AutofillServiceSelector
    val autofillAuthListener: AutofillAuthListener
}