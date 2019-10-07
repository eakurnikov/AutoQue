package com.eakurnikov.autoque.autofill.impl.api.selector

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.autofill.AutofillManager
import android.widget.Toast
import com.eakurnikov.autoque.autofill.api.api.selector.AutofillServiceSelector
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.common.annotations.AppContext
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 */
class AutofillServiceSelectorImpl
@Inject constructor(
    @AppContext private val context: Context
) : AutofillServiceSelector {

    private val requestCode = 0

    override val isSelected: Boolean
        get() = context.getSystemService(AutofillManager::class.java).hasEnabledAutofillServices()

    override fun promptSelection(activity: Activity) {
        activity.startActivityForResult(
            Intent(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE).apply {
                data = Uri.parse("package:${context.packageName}")
            },
            requestCode
        )
    }

    override fun onSelection(
        activity: Activity,
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        action: AutofillServiceSelector.OnSelectionResultAction?
    ) {
        if (this.requestCode != requestCode) return

        when (resultCode) {
            Activity.RESULT_OK -> {
                Toast.makeText(
                    activity,
                    activity.getString(R.string.faf_autofill_service_selected),
                    Toast.LENGTH_LONG
                ).show()

                action?.onSelected()
            }
            Activity.RESULT_CANCELED -> {
                action?.onNotSelected()
            }
        }
    }
}