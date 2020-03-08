package com.eakurnikov.autoque.autofill.impl.internal.domain.providers.disclaimer

import android.content.IntentSender
import android.os.Bundle

/**
 * Created by eakurnikov on 2020-01-16
 */
interface DisclaimerProvider {

    fun getDisclaimerIntentSender(clientState: Bundle): IntentSender
}