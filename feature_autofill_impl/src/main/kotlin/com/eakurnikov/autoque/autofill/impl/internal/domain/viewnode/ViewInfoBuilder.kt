package com.eakurnikov.autoque.autofill.impl.internal.domain.viewnode

import android.annotation.TargetApi
import android.app.assist.AssistStructure
import android.os.Build
import android.view.autofill.AutofillId
import com.eakurnikov.autoque.autofill.impl.internal.data.model.ViewInfo
import com.eakurnikov.autoque.autofill.impl.internal.domain.heuristics.ViewNodeHeuristics
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-09
 */
@TargetApi(Build.VERSION_CODES.O)
class ViewInfoBuilder @Inject constructor(
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