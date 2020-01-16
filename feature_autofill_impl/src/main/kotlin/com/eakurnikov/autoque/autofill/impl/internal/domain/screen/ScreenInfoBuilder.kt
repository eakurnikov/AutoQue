package com.eakurnikov.autoque.autofill.impl.internal.domain.screen

import android.annotation.TargetApi
import android.app.assist.AssistStructure
import android.os.Build
import android.view.View
import com.eakurnikov.autoque.autofill.impl.internal.data.model.AuthFormInfo
import com.eakurnikov.autoque.autofill.impl.internal.data.model.ScreenInfo
import com.eakurnikov.autoque.autofill.impl.internal.data.model.ViewInfo
import com.eakurnikov.autoque.autofill.impl.internal.domain.viewnode.ViewInfoBuilder
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-09
 *
 * Builds [ScreenInfo].
 */
@TargetApi(Build.VERSION_CODES.O)
class ScreenInfoBuilder @Inject constructor(
    private val viewInfoBuilder: ViewInfoBuilder
) {
    private var login: ViewInfo? = null
    private var password: ViewInfo? = null

    @JvmName("buildFromAssistStructures")
    fun build(assistStructures: List<AssistStructure>): ScreenInfo? =
        build(assistStructures::forEachViewNode)

    @JvmName("buildFromViewNodes")
    fun build(viewNodes: List<AssistStructure.ViewNode>): ScreenInfo? =
        build(viewNodes::forEach)

    private inline fun build(
        crossinline forEachViewNode: ((AssistStructure.ViewNode) -> Unit) -> Unit
    ): ScreenInfo? {

        login = null
        password = null

        forEachViewNode { viewNode: AssistStructure.ViewNode ->
            viewInfoBuilder.build(viewNode)?.let { viewInfo: ViewInfo ->
                when (viewInfo.autofillHint) {
                    View.AUTOFILL_HINT_USERNAME -> onUsername(viewInfo)
                    View.AUTOFILL_HINT_PASSWORD -> onPassword(viewInfo)
                    View.AUTOFILL_HINT_EMAIL_ADDRESS -> onEmail(viewInfo)
                    View.AUTOFILL_HINT_PHONE -> onPhone(viewInfo)
                }
            }
        }

        login ?: password ?: return null

        return ScreenInfo(AuthFormInfo(login, password))
    }

    private fun onUsername(viewInfo: ViewInfo) {
        login = viewInfo
    }

    private fun onPassword(viewInfo: ViewInfo) {
        password = viewInfo
    }

    private fun onEmail(viewInfo: ViewInfo) {
        if (login == null) {
            login = viewInfo
        }
    }

    private fun onPhone(viewInfo: ViewInfo) {
        if (login == null) {
            login = viewInfo
        }
    }
}