package com.eakurnikov.autoque.ui.view.popup

import android.content.Context
import com.eakurnikov.autoque.domain.popup.PopupNotificationManager
import com.eakurnikov.common.annotations.AppContext
import com.eakurnikov.common.di.annotations.AppScope
import javax.inject.Inject

@AppScope
class PopupFactory @Inject constructor(
    @AppContext private val context: Context,
    private val popupNotificationManager: PopupNotificationManager
) {
    fun createAutofillPopup(): Popup = AutofillPopup(context, popupNotificationManager)
}