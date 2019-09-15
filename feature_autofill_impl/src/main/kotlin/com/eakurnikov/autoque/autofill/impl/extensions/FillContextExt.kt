package com.eakurnikov.autoque.autofill.impl.extensions

import android.app.assist.AssistStructure
import android.service.autofill.FillContext
import android.view.autofill.AutofillId

/**
 * Created by eakurnikov on 2019-09-15
 */

fun FillContext.findViewNodes(autofillIds: Array<AutofillId>): Array<AssistStructure.ViewNode?> {
    return javaClass
        .getMethod("findViewNodesByAutofillIds", Array<AutofillId>::class.java)
        .invoke(this, autofillIds) as Array<AssistStructure.ViewNode?>
}