package com.eakurnikov.autoque.autofill.api.dependencies.ui.update

import android.app.Activity
import com.eakurnikov.autoque.autofill.api.api.presentation.update.AutofillUpdatePromptPresenter

/**
 * Created by eakurnikov on 2020-01-16
 */
interface AutofillUpdatePromptUi {

    val activityContext: Activity

    val autofillUpdatePromptPresenter: AutofillUpdatePromptPresenter

    fun finish()
}