package com.eakurnikov.autoque.autofill.api.dependencies.domain.disclaimer

import android.content.IntentSender
import android.os.Bundle
import com.eakurnikov.autoque.autofill.api.dependencies.ui.disclaimer.AutofillDisclaimerUi

/**
 * Created by eakurnikov on 2020-01-16
 */
interface AutofillDisclaimerProvider<T : AutofillDisclaimerUi> {

    val autofillDisclaimerUiClass: Class<T>

    fun getDisclaimerIntentSender(clientState: Bundle): IntentSender
}