package com.eakurnikov.autoque.autofill.impl.api.selector

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.autofill.AutofillManager
import com.eakurnikov.autoque.autofill.api.api.selector.AutofillServiceSelector
import com.eakurnikov.autoque.autofill.api.api.selector.AutofillServiceSelector.SelectionStatus
import com.eakurnikov.common.annotations.AppContext
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 */
class AutofillServiceSelectorImpl
@Inject constructor(
    @AppContext private val context: Context
) : AutofillServiceSelector {

    private val requestCode = 0

    private val autofillManager: AutofillManager
        get() = context.getSystemService(AutofillManager::class.java)

    override val selectionStatusSubject: BehaviorSubject<SelectionStatus> = BehaviorSubject.createDefault(
        SelectionStatus(isSelected, null)
    )

    override val isSelected: Boolean
        get() = autofillManager.hasEnabledAutofillServices()

    override fun unselect() {
        autofillManager.disableAutofillServices()
        selectionStatusSubject.onNext(
            SelectionStatus(false, null)
        )
    }

    override fun promptSelection(activity: Activity) {
        activity.startActivityForResult(
            Intent(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE).apply {
                data = Uri.parse("package:${context.packageName}")
            },
            requestCode
        )
    }

    override fun onSelection(requestCode: Int, resultCode: Int, data: Intent?): BehaviorSubject<SelectionStatus>? {
        if (this.requestCode != requestCode) return null
        return selectionStatusSubject.also { it.onNext(SelectionStatus(resultCode == Activity.RESULT_OK, data)) }
    }
}