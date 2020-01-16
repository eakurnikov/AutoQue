package com.eakurnikov.autoque.autofill.api.dependencies.ui.disclaimer

import android.app.Activity
import com.eakurnikov.autoque.autofill.api.api.presentation.disclaimer.AutofillDisclaimerPresenter

/**
 * Created by eakurnikov on 2020-01-16
 */
interface AutofillDisclaimerUi {

    val activityContext: Activity

    val autofillDisclaimerPresenter: AutofillDisclaimerPresenter?

    fun finish()
}