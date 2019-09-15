package com.eakurnikov.autoque.autofill.api.api.selector

import android.app.Activity
import android.content.Intent

/**
 * Created by eakurnikov on 2019-09-14
 */
interface AutofillServiceSelector {

    val isSelected: Boolean

    fun promptSelection(activity: Activity)

    fun onSelection(
        activity: Activity,
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        action: OnSelectionResultAction?
    )

    interface OnSelectionResultAction {

        fun onSelected()

        fun onNotSelected()
    }
}