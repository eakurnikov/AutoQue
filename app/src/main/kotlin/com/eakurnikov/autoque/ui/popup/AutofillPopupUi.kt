package com.eakurnikov.autoque.ui.popup

import com.eakurnikov.autoque.ui.view.popup.Popup
import com.eakurnikov.autoque.ui.view.popup.PopupFactory
import javax.inject.Inject

class AutofillPopupUi @Inject constructor(
    private val popupFactory: PopupFactory
) {
    private var autofillPopup: Popup? = null

    fun show() {
        if (autofillPopup != null) hide()
        autofillPopup = popupFactory.createAutofillPopup().apply { show() }
    }

    fun hide() {
        if (autofillPopup != null) {
            autofillPopup?.cancelIfNecessary()
            autofillPopup = null
        }
    }
}