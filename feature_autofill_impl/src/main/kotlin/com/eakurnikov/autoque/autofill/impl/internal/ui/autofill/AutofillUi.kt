package com.eakurnikov.autoque.autofill.impl.internal.ui.autofill

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.StringRes
import com.eakurnikov.common.annotations.AppContext
import com.eakurnikov.common.ui.SmartToastFactory
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-16
 */
class AutofillUi @Inject constructor(
    @AppContext private val context: Context,
    private val autofillViewBuilder: AutofillViewBuilder
) {
    private val showLengthMs: Int = 3_500

    fun showAsToast(@StringRes messageResId: Int) {
        showAsToast(context.getString(messageResId))
    }

    fun showAsToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showAsAutoQueToast(@StringRes messageResId: Int) {
        showAsAutoQueToast(context.getString(messageResId))
    }

    fun showAsAutoQueToast(message: String) {
        SmartToastFactory
            .create(
                context,
                autofillViewBuilder.buildAutoQueToast(message).apply(context, null),
                Gravity.BOTTOM or Gravity.FILL_HORIZONTAL,
                showLengthMs,
                true
            )
            .show()
    }
}