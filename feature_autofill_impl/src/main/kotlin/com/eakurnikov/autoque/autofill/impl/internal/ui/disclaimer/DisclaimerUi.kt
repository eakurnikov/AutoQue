package com.eakurnikov.autoque.autofill.impl.internal.ui.disclaimer

import android.app.Activity

/**
 * Created by eakurnikov on 2019-09-14
 */
interface DisclaimerUi {

    val activityContext: Activity

    fun finish()
}