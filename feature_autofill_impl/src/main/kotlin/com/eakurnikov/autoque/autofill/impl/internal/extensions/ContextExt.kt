package com.eakurnikov.autoque.autofill.impl.internal.extensions

import android.annotation.TargetApi
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.view.autofill.AutofillManager

/**
 * Created by eakurnikov on 2019-09-15
 */
fun Context.setComponentEnabled(componentClass: Class<*>, enable: Boolean) {
    packageManager.setComponentEnabledSetting(
        ComponentName(this, componentClass),
        if (enable) {
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        } else {
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED
        },
        PackageManager.DONT_KILL_APP
    )
}

fun Context.isComponentEnabled(componentClass: Class<*>): Boolean {
    return packageManager.getComponentEnabledSetting(ComponentName(this, componentClass)) ==
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED
}

@TargetApi(Build.VERSION_CODES.O)
fun Context.isAutofillServiceSelected(): Boolean {
    return getSystemService(AutofillManager::class.java)
        ?.hasEnabledAutofillServices()
        ?: false
}

@TargetApi(Build.VERSION_CODES.O)
fun Context.unselectAutofillService(): Boolean {
    return getSystemService(AutofillManager::class.java)
        ?.let { autofillManager: AutofillManager ->
            autofillManager.disableAutofillServices()
            true
        }
        ?: false
}