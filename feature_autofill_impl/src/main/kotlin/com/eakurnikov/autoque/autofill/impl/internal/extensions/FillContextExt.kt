package com.eakurnikov.autoque.autofill.impl.internal.extensions

import android.annotation.TargetApi
import android.app.assist.AssistStructure
import android.os.Build
import android.service.autofill.FillContext
import android.util.SparseIntArray
import android.view.autofill.AutofillId
import java.util.LinkedList

/**
 * Created by eakurnikov on 2019-09-15
 */
@TargetApi(Build.VERSION_CODES.O)
fun FillContext.findViewNodes(autofillIds: Array<AutofillId>): Array<AssistStructure.ViewNode?> {
    val viewNodesToProcess = LinkedList<AssistStructure.ViewNode>()
    val foundViewNodes = arrayOfNulls<AssistStructure.ViewNode>(autofillIds.size)

    // Indexes of foundViewNodes that are not found yet
    val missingViewNodeIndexes = SparseIntArray(autofillIds.size)

    for (autofillId in autofillIds.indices) {
        missingViewNodeIndexes.put(autofillId, /* ignored */ 0)
    }

    val numWindowNodes = structure.windowNodeCount
    for (i in 0 until numWindowNodes) {
        viewNodesToProcess.add(structure.getWindowNodeAt(i).rootViewNode)
    }

    while (missingViewNodeIndexes.size() > 0 && !viewNodesToProcess.isEmpty()) {
        val node = viewNodesToProcess.removeFirst()

        for (i in 0 until missingViewNodeIndexes.size()) {
            val index = missingViewNodeIndexes.keyAt(i)
            val id = autofillIds[index]

            if (id == node.autofillId) {
                foundViewNodes[index] = node
                missingViewNodeIndexes.removeAt(i)
                break
            }
        }

        for (i in 0 until node.childCount) {
            viewNodesToProcess.addLast(node.getChildAt(i))
        }
    }

    return foundViewNodes
}