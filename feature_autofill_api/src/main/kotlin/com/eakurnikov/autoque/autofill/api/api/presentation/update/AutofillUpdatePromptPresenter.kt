package com.eakurnikov.autoque.autofill.api.api.presentation.update

import com.eakurnikov.autoque.autofill.api.dependencies.data.model.AutofillPayload
import com.eakurnikov.autoque.autofill.api.dependencies.ui.update.AutofillUpdatePromptUi

/**
 * Created by eakurnikov on 2020-01-16
 */
interface AutofillUpdatePromptPresenter {

    fun onPromptResponse(
        promptUi: AutofillUpdatePromptUi,
        promptPayload: AutofillPayload?,
        shouldUpdate: Boolean
    )
}