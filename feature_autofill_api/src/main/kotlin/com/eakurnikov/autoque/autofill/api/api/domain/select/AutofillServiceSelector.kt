package com.eakurnikov.autoque.autofill.api.api.domain.select

import android.app.Activity
import android.content.Intent
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by eakurnikov on 2019-09-14
 */
interface AutofillServiceSelector {

    val isSelected: Boolean

    val selectionStatusSubject: BehaviorSubject<Boolean>

    fun unselect()

    fun promptSelection(activity: Activity, requestCode: Int)

    fun onSelection(isSelected: Boolean)
}