package com.eakurnikov.autoque.autofill.impl.extensions

import android.app.assist.AssistStructure
import android.service.autofill.FillContext
import android.util.SparseIntArray
import android.view.autofill.AutofillId
import java.util.LinkedList

/**
 * Created by eakurnikov on 2019-09-15
 */
fun FillContext.findViewNodes(autofillIds: Array<AutofillId>): Array<AssistStructure.ViewNode?> {
    val viewNodesToProcess: LinkedList<AssistStructure.ViewNode> = LinkedList()

    val windowNodeCount: Int = structure.windowNodeCount
    for (i in 0 until windowNodeCount) {
        viewNodesToProcess.add(
            structure.getWindowNodeAt(i).rootViewNode
        )
    }

    val missingViewNodeIndexes: SparseIntArray =
        SparseIntArray(autofillIds.size).apply { autofillIds.indices.forEach { put(it, 0) } }

    val foundViewNodes: Array<AssistStructure.ViewNode?> = arrayOfNulls(autofillIds.size)

    while (missingViewNodeIndexes.size() > 0 && !viewNodesToProcess.isEmpty()) {
        val viewNode: AssistStructure.ViewNode = viewNodesToProcess.removeFirst()

        for (i in 0 until missingViewNodeIndexes.size()) {
            val viewNodeIndex: Int = missingViewNodeIndexes.keyAt(i)
            val autofillId: AutofillId = autofillIds[viewNodeIndex]

            if (autofillId == viewNode.autofillId) {
                foundViewNodes[viewNodeIndex] = viewNode
                missingViewNodeIndexes.removeAt(i)
                break
            }
        }

        for (i in 0 until viewNode.childCount) {
            viewNodesToProcess.addLast(viewNode.getChildAt(i))
        }
    }

    return foundViewNodes
}