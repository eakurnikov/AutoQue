package com.eakurnikov.autoque.autofill.impl.domain.viewnode

import android.app.assist.AssistStructure
import android.view.autofill.AutofillId
import com.eakurnikov.autoque.autofill.impl.data.model.ViewInfo
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 */
class ViewInfoBuilder
@Inject constructor(
    private val viewNodeHeuristics: ViewNodeHeuristics
) {
    fun build(viewNode: AssistStructure.ViewNode): ViewInfo? {
        val autofillId: AutofillId = viewNode.autofillId ?: return null
        val autofillType: Int = viewNodeHeuristics.obtainAutofillType(viewNode) ?: return null
        val autofillHint: String = viewNodeHeuristics.obtainAutofillHint(viewNode) ?: return null
        val autofillValue: String? = viewNodeHeuristics.obtainAutofillValue(viewNode)
        val saveType: Int = viewNodeHeuristics.obtainSaveType(autofillHint)

        return ViewInfo(
            autofillId,
            autofillType,
            autofillHint,
            autofillValue,
            saveType,
            viewNode.isFocused
        )
    }
}