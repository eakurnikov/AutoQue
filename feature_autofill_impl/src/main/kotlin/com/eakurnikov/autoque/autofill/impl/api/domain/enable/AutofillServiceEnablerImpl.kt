package com.eakurnikov.autoque.autofill.impl.api.domain.enable

import android.content.Context
import android.content.Intent
import com.eakurnikov.autoque.autofill.api.api.domain.enable.AutofillServiceEnabler
import com.eakurnikov.autoque.autofill.impl.internal.domain.AutoQueAutofillService
import com.eakurnikov.autoque.autofill.impl.internal.extensions.isComponentEnabled
import com.eakurnikov.autoque.autofill.impl.internal.extensions.setComponentEnabled
import com.eakurnikov.common.annotations.AppContext
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 */
class AutofillServiceEnablerImpl @Inject constructor(
    @AppContext private val context: Context
) : AutofillServiceEnabler {

    private val autofillServiceClass = AutoQueAutofillService::class.java

    override var isEnabled: Boolean
        get() = context.isComponentEnabled(autofillServiceClass)
        set(value) {
            if (!value) {
                context.stopService(Intent(context, autofillServiceClass))
            }
            context.setComponentEnabled(AutoQueAutofillService::class.java, value)
        }
}