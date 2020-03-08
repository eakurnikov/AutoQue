package com.eakurnikov.autoque.autofill.impl.internal.ui.update

import android.app.Activity

/**
 * Created by eakurnikov on 2019-09-14
 */
interface UpdatePromptUi {

    val activityContext: Activity

    fun finish()
}