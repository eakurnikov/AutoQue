package com.eakurnikov.autoque.autofill.impl.api.domain.select

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.autofill.AutofillManager
import com.eakurnikov.autoque.autofill.api.api.domain.select.AutofillServiceSelector
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

    private val autofillManager: AutofillManager?
        get() = context.getSystemService(AutofillManager::class.java)

    override val isSelected: Boolean = autofillManager?.hasEnabledAutofillServices() ?: false

    override val selectionStatusSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(isSelected)

    override fun unselect() {
        autofillManager?.let { autofillManager: AutofillManager ->
            autofillManager.disableAutofillServices()
            selectionStatusSubject.onNext(false)
        }
    }

    override fun promptSelection(activity: Activity, requestCode: Int) {
        activity.startActivityForResult(
            Intent(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE).apply {
                data = Uri.parse("package:${context.packageName}")
            },
            requestCode
        )
    }

    override fun onSelection(isSelected: Boolean) {
        selectionStatusSubject.onNext(isSelected)
    }
}