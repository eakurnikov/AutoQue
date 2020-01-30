package com.eakurnikov.autoque.autofill.impl.api.domain.select

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.eakurnikov.autoque.autofill.api.api.domain.select.AutofillServiceSelector
import com.eakurnikov.autoque.autofill.impl.internal.extensions.isAutofillServiceSelected
import com.eakurnikov.autoque.autofill.impl.internal.extensions.unselectAutofillService
import com.eakurnikov.common.annotations.AppContext
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 */
@TargetApi(Build.VERSION_CODES.O)
class AutofillServiceSelectorImpl @Inject constructor(
    @AppContext private val context: Context
) : AutofillServiceSelector {

    override val isSelected: Boolean
        get() = context.isAutofillServiceSelected()

    override val selectionSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(isSelected)

    override fun unselect(): Unit =
        if (context.unselectAutofillService()) selectionSubject.onNext(false) else Unit

    override fun promptSelection(activity: Activity, requestCode: Int): Boolean {
        val selectionIntent = Intent(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE).apply {
            data = Uri.parse("package:${context.packageName}")
        }

        val activityExists: Boolean =
            selectionIntent.resolveActivityInfo(context.packageManager, 0) != null

        if (activityExists) {
            activity.startActivityForResult(selectionIntent, requestCode)
        }

        return activityExists
    }

    override fun onSelection(isSelected: Boolean) {
        selectionSubject.onNext(isSelected)
    }
}