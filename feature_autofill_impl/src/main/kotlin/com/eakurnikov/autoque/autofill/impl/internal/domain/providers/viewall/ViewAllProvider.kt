package com.eakurnikov.autoque.autofill.impl.internal.domain.providers.viewall

import android.content.IntentSender
import android.os.Bundle

/**
 * Created by eakurnikov on 2020-01-16
 */
interface ViewAllProvider {

    fun getViewAllIntentSender(clientState: Bundle): IntentSender
}