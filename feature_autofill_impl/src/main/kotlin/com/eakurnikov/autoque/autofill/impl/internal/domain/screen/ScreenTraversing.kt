package com.eakurnikov.autoque.autofill.impl.internal.domain.screen

import android.annotation.TargetApi
import android.app.assist.AssistStructure
import android.os.Build

/**
 * Created by eakurnikov on 2020-01-09
 *
 * Traverses through each view node of assist structure and applies an action.
 */
@TargetApi(Build.VERSION_CODES.O)
private class ScreenTraversing(
    private val assistStructures: List<AssistStructure>,
    private inline val onEachViewNode: (AssistStructure.ViewNode) -> Unit
) {
    operator fun invoke() {
        assistStructures.forEach { assistStructure: AssistStructure ->
            val windowNodesCount: Int = assistStructure.windowNodeCount

            for (i in 0 until windowNodesCount) {
                onViewNode(assistStructure.getWindowNodeAt(i).rootViewNode)
            }
        }
    }

    private fun onViewNode(rootViewNode: AssistStructure.ViewNode) {
        onEachViewNode(rootViewNode)

        val childCount = rootViewNode.childCount

        for (i in 0 until childCount) {
            onViewNode(rootViewNode.getChildAt(i))
        }
    }
}

fun List<AssistStructure>.forEachViewNode(action: (AssistStructure.ViewNode) -> Unit): Unit =
    ScreenTraversing(this, action)()