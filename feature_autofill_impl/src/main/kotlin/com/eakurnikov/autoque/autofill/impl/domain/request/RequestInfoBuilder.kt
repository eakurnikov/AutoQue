package com.eakurnikov.autoque.autofill.impl.domain.request

import android.app.assist.AssistStructure
import android.service.autofill.FillContext
import android.service.autofill.FillRequest
import android.service.autofill.SaveRequest
import android.view.autofill.AutofillId
import com.eakurnikov.autoque.autofill.api.dependencies.packagename.PackageVerifier
import com.eakurnikov.autoque.autofill.impl.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.data.model.ScreenInfo
import com.eakurnikov.autoque.autofill.impl.domain.AutoQueAutofillService
import com.eakurnikov.autoque.autofill.impl.domain.screen.ScreenInfoBuilder
import com.eakurnikov.autoque.autofill.impl.extensions.autofillIdsAsArray
import com.eakurnikov.autoque.autofill.impl.extensions.findViewNodes
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 *
 * Checks if an autofill service's [FillRequest] and [SaveRequest] can be handled by
 * [AutoQueAutofillService]. Now marks as verified requests only from single-screen forms with login and
 * password fields.
 */
class RequestInfoBuilder @Inject constructor(
    private val packageVerifier: PackageVerifier,
    private val screenInfoBuilder: ScreenInfoBuilder
) {
    fun build(fillRequest: FillRequest): RequestInfo? {
        val fillContexts: List<FillContext> = fillRequest.fillContexts.filter { it.requestId == fillRequest.id }
        val clientPackageName: String = fillContexts.last().structure.activityComponent.packageName

        if (!packageVerifier.verifyPackage(clientPackageName)) return null

        val assistStructures: List<AssistStructure> = fillContexts.map { it.structure }
        val screenInfo: ScreenInfo = screenInfoBuilder.build(assistStructures) ?: return null

        return RequestInfo(listOf(fillRequest.id), clientPackageName, screenInfo)
    }

    fun build(saveRequest: SaveRequest, fillRequestInfo: RequestInfo): RequestInfo? {
        val fillContexts: List<FillContext> =
            saveRequest.fillContexts.filter { it.requestId in fillRequestInfo.requestIds }
        val clientPackageName: String = fillContexts.last().structure.activityComponent.packageName

        if (!packageVerifier.verifyPackage(clientPackageName)) return null

        val filledAutofillIds: Array<AutofillId> = fillRequestInfo.screenInfo.authFormInfo.autofillIdsAsArray
        val viewNodes: List<AssistStructure.ViewNode> =
            fillContexts.flatMap { it.findViewNodes(filledAutofillIds).filterNotNull() }
        val screenInfo: ScreenInfo = screenInfoBuilder.build(viewNodes) ?: return null

        return RequestInfo(fillRequestInfo.requestIds, clientPackageName, screenInfo)
    }
}