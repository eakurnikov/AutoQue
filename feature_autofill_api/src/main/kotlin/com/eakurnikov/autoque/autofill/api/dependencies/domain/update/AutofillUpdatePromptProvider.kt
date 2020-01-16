package com.eakurnikov.autoque.autofill.api.dependencies.domain.update

import android.content.IntentSender
import android.os.Bundle
import com.eakurnikov.autoque.autofill.api.dependencies.ui.update.AutofillUpdatePromptUi

/**
 * Created by eakurnikov on 2020-01-16
 */
interface AutofillUpdatePromptProvider<T : AutofillUpdatePromptUi> {

    val autofillUpdatePromptUiClass: Class<T>

    fun getPromptIntentSender(clientState: Bundle): IntentSender
}