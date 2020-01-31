package com.eakurnikov.autoque.ui.view.popup

import android.content.Context
import com.eakurnikov.autoque.R
import com.eakurnikov.autoque.domain.popup.PopupNotificationManager
import com.eakurnikov.common.annotations.AppContext

class AutofillPopup(
    @AppContext context: Context,
    popupNotificationManager: PopupNotificationManager
) : Popup(
    context,
    popupNotificationManager,
    R.layout.faf_popup_toast,
    R.layout.faf_popup_notification
)