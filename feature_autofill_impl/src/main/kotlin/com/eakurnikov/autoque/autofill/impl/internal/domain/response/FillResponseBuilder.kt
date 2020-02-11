package com.eakurnikov.autoque.autofill.impl.internal.domain.response

import android.annotation.TargetApi
import android.content.Context
import android.content.IntentSender
import android.os.Build
import android.os.Bundle
import android.service.autofill.CustomDescription
import android.service.autofill.FillResponse
import android.service.autofill.SaveInfo
import android.view.autofill.AutofillId
import android.widget.RemoteViews
import com.eakurnikov.autoque.autofill.api.dependencies.domain.verification.AutofillClientVerifier
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataDto
import com.eakurnikov.autoque.autofill.impl.internal.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.internal.extensions.autofillIdsAsArray
import com.eakurnikov.autoque.autofill.impl.internal.extensions.compositeSaveType
import com.eakurnikov.autoque.autofill.impl.internal.extensions.containsEntityWithPackageName
import com.eakurnikov.autoque.autofill.impl.internal.ui.AutofillViewBuilder
import com.eakurnikov.common.annotations.AppContext
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-09
 *
 * Builds a [FillResponse] using [AutofillViewBuilder] and [DatasetBuilder].
 */
@TargetApi(Build.VERSION_CODES.O)
class FillResponseBuilder @Inject constructor(
    @AppContext private val context: Context,
    private val autofillClientVerifier: AutofillClientVerifier,
    private val autofillViewBuilder: AutofillViewBuilder,
    private val datasetBuilder: DatasetBuilder
) {
    fun buildLocked(
        authIntentSender: IntentSender,
        fillRequestInfo: RequestInfo,
        clientState: Bundle
    ): FillResponse {

        val autofillIds: Array<AutofillId> =
            fillRequestInfo.screenInfo.authFormInfo.autofillIdsAsArray

        return FillResponse
            .Builder()
            .setClientState(clientState)
            .addAuthentication(autofillIds, authIntentSender)
            .addSaveInfo(autofillIds, fillRequestInfo)
            .build()
    }

    fun buildUnlocked(
        fillDataDtos: List<FillDataDto>,
        fillRequestInfo: RequestInfo,
        clientState: Bundle
    ): FillResponse {

        val autofillIds: Array<AutofillId> =
            fillRequestInfo.screenInfo.authFormInfo.autofillIdsAsArray

        return FillResponse
            .Builder()
            .setClientState(clientState)
            .addDatasets(fillRequestInfo, fillDataDtos, clientState)
            .addSaveInfo(autofillIds, fillRequestInfo)
            .addHeader(fillRequestInfo.clientPackageName, fillDataDtos)
            .build()
    }

    fun buildEmpty(fillRequestInfo: RequestInfo, clientState: Bundle): FillResponse {
        val autofillIds: Array<AutofillId> =
            fillRequestInfo.screenInfo.authFormInfo.autofillIdsAsArray

        return FillResponse
            .Builder()
            .setClientState(clientState)
            .addSaveInfo(autofillIds, fillRequestInfo)
            .build()
    }

    private fun FillResponse.Builder.addAuthentication(
        autofillIds: Array<AutofillId>,
        authIntentSender: IntentSender
    ): FillResponse.Builder {

        return setAuthentication(
            autofillIds,
            authIntentSender,
            autofillViewBuilder.buildDatasetAuthItemView()
        )
    }

    private fun FillResponse.Builder.addSaveInfo(
        autofillIds: Array<AutofillId>,
        fillRequestInfo: RequestInfo
    ): FillResponse.Builder {

        if (isAuthFormFound(autofillIds)) {
            val compositeSaveType: Int = fillRequestInfo.screenInfo.authFormInfo.compositeSaveType
            val saveInfo: SaveInfo = SaveInfo
                .Builder(compositeSaveType, autofillIds)
                .setFlags(SaveInfo.FLAG_SAVE_ON_ALL_VIEWS_INVISIBLE)
                .customizeView()
                .build()

            setSaveInfo(saveInfo)
        }

        return this
    }

    private fun FillResponse.Builder.addDatasets(
        fillRequestInfo: RequestInfo,
        fillDataDtos: List<FillDataDto>,
        clientState: Bundle
    ): FillResponse.Builder {

        val isClientPackageNameVerified: Boolean =
            autofillClientVerifier.isInstallerSafe(fillRequestInfo.clientPackageName)

        if (isClientPackageNameVerified) {
            fillDataDtos.forEach { fillDataDto: FillDataDto ->
                addDataset(
                    datasetBuilder.buildUnlocked(
                        fillRequestInfo.screenInfo.authFormInfo,
                        fillDataDto
                    )
                )
            }
        } else {
            fillDataDtos.forEach { fillDataDto: FillDataDto ->
                addDataset(
                    datasetBuilder.buildLocked(
                        fillRequestInfo.screenInfo.authFormInfo,
                        fillDataDto,
                        clientState
                    )
                )
            }
        }

        return this
    }

    private fun FillResponse.Builder.addHeader(
        clientPackageName: String,
        fillDataDtos: List<FillDataDto>
    ): FillResponse.Builder {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P &&
            !fillDataDtos.containsEntityWithPackageName(clientPackageName)
        ) {
            setHeader(autofillViewBuilder.buildDatasetHeaderView())
        }

        return this
    }

    private fun SaveInfo.Builder.customizeView(): SaveInfo.Builder {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> {
                val saveDescriptionView: RemoteViews =
                    autofillViewBuilder.buildSaveDescriptionView()

                val customDescription: CustomDescription =
                    CustomDescription.Builder(saveDescriptionView).build()

                setCustomDescription(customDescription)
            }
            else -> {
                setDescription(context.getString(R.string.faf_save_description))
            }
        }
    }

    private fun isAuthFormFound(autofillIds: Array<AutofillId>): Boolean = autofillIds.size == 2
}