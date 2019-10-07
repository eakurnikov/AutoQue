package com.eakurnikov.autoque.autofill.impl.extensions

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

/**
 * Created by eakurnikov on 2019-09-15
 */
fun Context.setComponentEnabled(componentClass: Class<*>, enable: Boolean) {
    packageManager.setComponentEnabledSetting(
        ComponentName(this, componentClass),
        if (enable)
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        else
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
        PackageManager.DONT_KILL_APP
    )
}

fun Context.isComponentEnabled(componentClass: Class<*>): Boolean {
    return packageManager.getComponentEnabledSetting(ComponentName(this, componentClass)) ==
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED
}