package com.eakurnikov.autoque.autofill.impl.api.registrar

import android.content.Context
import android.content.Intent
import com.eakurnikov.autoque.autofill.api.api.registrar.AutofillServiceRegistrar
import com.eakurnikov.autoque.autofill.impl.domain.AutoQueAutofillService
import com.eakurnikov.autoque.autofill.impl.extensions.isComponentEnabled
import com.eakurnikov.autoque.autofill.impl.extensions.setComponentEnabled
import com.eakurnikov.common.annotations.AppContext
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 */
class AutofillServiceRegistrarImpl
@Inject constructor(
    @AppContext private val context: Context
) : AutofillServiceRegistrar {

    override var isRegistered: Boolean
        get() = context.isComponentEnabled(autofillServiceClass)
        set(value) {
            if (!value) context.stopService(Intent(context, autofillServiceClass))
            context.setComponentEnabled(AutoQueAutofillService::class.java, value)
        }

    private val autofillServiceClass = AutoQueAutofillService::class.java
}