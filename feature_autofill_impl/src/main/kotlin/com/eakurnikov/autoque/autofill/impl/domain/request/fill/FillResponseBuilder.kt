package com.eakurnikov.autoque.autofill.impl.domain.request.fill

import android.content.Context
import android.content.IntentSender
import android.os.Build
import android.os.Bundle
import android.service.autofill.CustomDescription
import android.service.autofill.FillResponse
import android.service.autofill.SaveInfo
import android.view.autofill.AutofillId
import android.widget.RemoteViews
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.autoque.autofill.impl.data.model.FillDataEntity
import com.eakurnikov.autoque.autofill.impl.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.data.model.ScreenInfo
import com.eakurnikov.autoque.autofill.impl.extensions.autofillIdsAsArray
import com.eakurnikov.autoque.autofill.impl.extensions.compositeSaveType
import com.eakurnikov.autoque.autofill.impl.extensions.containsEntityWithPackageName
import com.eakurnikov.common.annotations.AppContext
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 *
 * Builds a [FillResponse] using [AutofillViewProducer] and [DatasetBuilder].
 */
class FillResponseBuilder @Inject constructor(
    @AppContext private val context: Context,
    private val autofillViewProducer: AutofillViewProducer,
    private val datasetBuilder: DatasetBuilder
) {
    fun buildLocked(
        authIntentSender: IntentSender,
        requestInfo: RequestInfo,
        clientState: Bundle
    ): FillResponse {

        val autofillIds: Array<AutofillId> = requestInfo.screenInfo.authFormInfo.autofillIdsAsArray

        return FillResponse
            .Builder()
            .setClientState(clientState)
            .addAuthentication(autofillIds, authIntentSender)
            .addSaveInfo(autofillIds, requestInfo)
            .build()
    }

    fun buildUnlocked(
        fillDataEntities: List<FillDataEntity>,
        requestInfo: RequestInfo,
        clientState: Bundle
    ): FillResponse {

        val autofillIds: Array<AutofillId> = requestInfo.screenInfo.authFormInfo.autofillIdsAsArray

        return FillResponse
            .Builder()
            .setClientState(clientState)
            .addDatasets(requestInfo.screenInfo, fillDataEntities)
            .addSaveInfo(autofillIds, requestInfo)
            .addHeader(requestInfo, fillDataEntities)
            .build()
    }

    private fun FillResponse.Builder.addAuthentication(
        autofillIds: Array<AutofillId>,
        authIntentSender: IntentSender
    ): FillResponse.Builder {

        return setAuthentication(
            autofillIds,
            authIntentSender,
            autofillViewProducer.datasetAuthItemView()
        )
    }

    private fun FillResponse.Builder.addSaveInfo(
        autofillIds: Array<AutofillId>,
        requestInfo: RequestInfo
    ): FillResponse.Builder {

        if (isAuthFormFound(autofillIds)) {
            val compositeSaveType: Int = requestInfo.screenInfo.authFormInfo.compositeSaveType

            setSaveInfo(
                SaveInfo.Builder(compositeSaveType, autofillIds)
                    .setFlags(SaveInfo.FLAG_SAVE_ON_ALL_VIEWS_INVISIBLE)
                    .customize()
                    .build()
            )
        }

        return this
    }

    private fun FillResponse.Builder.addDatasets(
        screenInfo: ScreenInfo,
        fillDataEntities: List<FillDataEntity>
    ): FillResponse.Builder {

        if (fillDataEntities.isEmpty()) {
            addDataset(datasetBuilder.buildStub(screenInfo.authFormInfo))
        } else {
            fillDataEntities.forEach { fillDataEntity: FillDataEntity ->
                addDataset(datasetBuilder.build(screenInfo.authFormInfo, fillDataEntity))
            }
        }

        return this
    }

    private fun FillResponse.Builder.addHeader(
        requestInfo: RequestInfo,
        fillDataEntities: List<FillDataEntity>
    ): FillResponse.Builder {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            when {
                fillDataEntities.isEmpty() -> {
                    setHeader(autofillViewProducer.noDatasetsHeaderItemView())
                }
                !fillDataEntities.containsEntityWithPackageName(requestInfo.clientPackageName) -> {
                    setHeader(autofillViewProducer.datasetHeaderItemView())
                }
            }
        }

        return this
    }

    private fun SaveInfo.Builder.customize(): SaveInfo.Builder {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> {
                val saveDescriptionView: RemoteViews = autofillViewProducer.saveDescriptionView()

                val customDescription: CustomDescription =
                    CustomDescription.Builder(saveDescriptionView).build()

                setCustomDescription(customDescription)
            }
            else -> {
                setDescription(context.getString(R.string.faf_save_description_title))
            }
        }
    }

    private fun isAuthFormFound(autofillIds: Array<AutofillId>): Boolean = autofillIds.size == 2
}