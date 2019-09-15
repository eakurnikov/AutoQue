package com.eakurnikov.autoque.autofill.impl.domain.screen

import android.app.assist.AssistStructure
import android.view.View
import com.eakurnikov.autoque.autofill.impl.data.model.AuthFormInfo
import com.eakurnikov.autoque.autofill.impl.data.model.ScreenInfo
import com.eakurnikov.autoque.autofill.impl.data.model.ViewInfo
import com.eakurnikov.autoque.autofill.impl.domain.viewnode.ViewInfoBuilder
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 *
 * Builds [ScreenInfo].
 */
class ScreenInfoBuilder @Inject constructor(
    private val viewInfoBuilder: ViewInfoBuilder
) {
    private var login: ViewInfo? = null
    private var password: ViewInfo? = null
    private val webDomainBuilder: StringBuilder = StringBuilder()

    @JvmName("buildFromAssistStructures")
    fun build(assistStructures: List<AssistStructure>): ScreenInfo? = build(assistStructures::forEachViewNode)

    @JvmName("buildFromViewNodes")
    fun build(viewNodes: List<AssistStructure.ViewNode>): ScreenInfo? = build(viewNodes::forEach)

    private inline fun build(crossinline forEachViewNode: ((AssistStructure.ViewNode) -> Unit) -> Unit): ScreenInfo? {
        login = null
        password = null
        webDomainBuilder.setLength(0)

        forEachViewNode { viewNode: AssistStructure.ViewNode ->
            onWebDomain(viewNode.webDomain)

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

        return ScreenInfo(
            AuthFormInfo(login, password),
            webDomainBuilder.toString()
        )
    }

    private fun onWebDomain(webDomain: String?) {
        webDomain ?: return

        if (webDomainBuilder.isNotEmpty() && webDomain != webDomainBuilder.toString()) {
            throw SecurityException(
                "Found multiple web domains: valid=$webDomainBuilder, child=$webDomain"
            )
        } else {
            webDomainBuilder.append(webDomain)
        }
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