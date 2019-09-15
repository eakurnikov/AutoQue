package com.eakurnikov.autoque.autofill.impl.domain.screen

import android.app.assist.AssistStructure

/**
 * Created by eakurnikov on 2019-09-15
 *
 * Traverses through each view node of assist structure and applies an action.
 */
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