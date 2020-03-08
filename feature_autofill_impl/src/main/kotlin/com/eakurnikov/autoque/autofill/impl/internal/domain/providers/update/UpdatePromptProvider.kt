package com.eakurnikov.autoque.autofill.impl.internal.domain.providers.update

import android.content.IntentSender
import android.os.Bundle

/**
 * Created by eakurnikov on 2020-01-16
 */
interface UpdatePromptProvider {

    fun getPromptIntentSender(clientState: Bundle): IntentSender
}