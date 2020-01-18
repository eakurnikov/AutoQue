package com.eakurnikov.autoque.autofill.impl.internal.domain.usecase.fill

import android.content.IntentSender
import android.os.Bundle
import android.service.autofill.Dataset
import com.eakurnikov.autoque.autofill.api.dependencies.domain.auth.AutofillAuthProvider
import com.eakurnikov.autoque.autofill.impl.internal.data.enums.UnsafeDatasetType
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataDto
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataId
import com.eakurnikov.autoque.autofill.impl.internal.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.internal.data.model.UnsafeDatasetResource
import com.eakurnikov.autoque.autofill.impl.internal.data.repositories.AutofillRepository
import com.eakurnikov.autoque.autofill.impl.internal.domain.response.DatasetBuilder
import com.eakurnikov.autoque.autofill.impl.internal.extensions.log
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-16
 */
class ProduceUnsafeDatasetUseCase @Inject constructor(
    private val autofillRepo: AutofillRepository,
    private val authProvider: AutofillAuthProvider<*>,
    private val datasetBuilder: DatasetBuilder
) {
    private val tag: String = "ProduceUnsafeDatasetUseCase"

    operator fun invoke(
        fillDataId: FillDataId,
        fillRequestInfo: RequestInfo,
        clientState: Bundle
    ): Single<UnsafeDatasetResource> {

        return if (authProvider.isAuthRequired) {
            val authIntentSender: IntentSender =
                authProvider.getUnsafeFillAuthIntentSender(clientState)
            val resource =
                UnsafeDatasetResource(null, authIntentSender, UnsafeDatasetType.LOCKED)
            Single.just(resource)
        } else {
            autofillRepo
                .getFillDataById(fillDataId)
                .map { fillDataDto: FillDataDto ->
                    val unsafeDataset: Dataset =
                        datasetBuilder.buildUnsafe(
                            fillRequestInfo.screenInfo.authFormInfo,
                            fillDataDto
                        )
                    UnsafeDatasetResource(unsafeDataset, null, UnsafeDatasetType.UNLOCKED)
                }
                .doOnError { e: Throwable ->
                    log("$tag: Error while building unsafe dataset: $e", e)
                }
        }
    }
}