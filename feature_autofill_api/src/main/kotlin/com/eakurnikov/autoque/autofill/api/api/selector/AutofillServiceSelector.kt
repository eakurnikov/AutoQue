package com.eakurnikov.autoque.autofill.api.api.selector

import android.app.Activity
import android.content.Intent
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by eakurnikov on 2019-09-14
 */
interface AutofillServiceSelector {

    val selectionStatusSubject: BehaviorSubject<SelectionStatus>

    val isSelected: Boolean

    fun unselect()

    fun promptSelection(activity: Activity)

    fun onSelection(requestCode: Int, resultCode: Int, data: Intent?): BehaviorSubject<SelectionStatus>?

    data class SelectionStatus(
        val isSelected: Boolean,
        val data: Intent?
    )
}