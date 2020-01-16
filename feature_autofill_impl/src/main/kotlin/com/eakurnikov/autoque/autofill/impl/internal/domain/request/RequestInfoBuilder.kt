package com.eakurnikov.autoque.autofill.impl.internal.domain.request

import android.annotation.TargetApi
import android.app.assist.AssistStructure
import android.os.Build
import android.service.autofill.FillContext
import android.service.autofill.FillRequest
import android.service.autofill.SaveRequest
import android.view.autofill.AutofillId
import com.eakurnikov.autoque.autofill.impl.internal.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.internal.data.model.ScreenInfo
import com.eakurnikov.autoque.autofill.impl.internal.domain.screen.ScreenInfoBuilder
import com.eakurnikov.autoque.autofill.impl.internal.extensions.autofillIdsAsArray
import com.eakurnikov.autoque.autofill.impl.internal.extensions.findViewNodes
import com.eakurnikov.autoque.autofill.impl.internal.extensions.log
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-09
 *
 * Checks if an autofill service's [FillRequest] and [SaveRequest] can be handled by
 * [KpmAutofillService]. Now marks as verified requests only from single-screen forms with login and
 * password fields.
 */
@TargetApi(Build.VERSION_CODES.O)
class RequestInfoBuilder @Inject constructor(
    private val screenInfoBuilder: ScreenInfoBuilder
) {
    fun build(fillRequest: FillRequest): RequestInfo? {
        val fillContexts: List<FillContext> =
            fillRequest.fillContexts.filter { it.requestId == fillRequest.id }

        val clientPackageName: String = fillContexts.last().structure.activityComponent.packageName

        val assistStructures: List<AssistStructure> = fillContexts.map { it.structure }

        val screenInfo: ScreenInfo = screenInfoBuilder.build(assistStructures) ?: run {
            log("Both login and password fields were not found while fill request")
            return null
        }

        return RequestInfo(listOf(fillRequest.id), clientPackageName, screenInfo)
    }

    fun build(saveRequest: SaveRequest, fillRequestInfo: RequestInfo): RequestInfo? {
        val fillContexts: List<FillContext> =
            saveRequest.fillContexts.filter { it.requestId in fillRequestInfo.requestIds }

        val clientPackageName: String = fillContexts.last().structure.activityComponent.packageName

        val filledAutofillIds: Array<AutofillId> =
            fillRequestInfo.screenInfo.authFormInfo.autofillIdsAsArray

        val viewNodes: List<AssistStructure.ViewNode> =
            fillContexts.flatMap { it.findViewNodes(filledAutofillIds).filterNotNull() }

        val screenInfo: ScreenInfo = screenInfoBuilder.build(viewNodes) ?: run {
            log("Both login and password fields were not found while save request")
            return null
        }

        return RequestInfo(fillRequestInfo.requestIds, clientPackageName, screenInfo)
    }
}