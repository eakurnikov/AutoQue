package com.eakurnikov.autoque.autofill.api.dependencies.ui.auth

import android.app.Activity
import com.eakurnikov.autoque.autofill.api.api.presentation.auth.AutofillAuthPresenter

/**
 * Created by eakurnikov on 2020-01-16
 */
interface AutofillAuthUi {

    val activityContext: Activity

    val autofillAuthPresenter: AutofillAuthPresenter

    fun finish()
}