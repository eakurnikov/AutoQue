package com.eakurnikov.autoque.autofill.impl.internal.domain.usecase.fill

import android.service.autofill.Dataset
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataDto
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataId
import com.eakurnikov.autoque.autofill.impl.internal.data.model.RequestInfo
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
    private val datasetBuilder: DatasetBuilder
) {
    private val tag: String = "ProduceUnsafeDatasetUseCase"

    operator fun invoke(
        fillDataId: FillDataId,
        fillRequestInfo: RequestInfo
    ): Single<Dataset> {

        return autofillRepo
            .getFillDataById(fillDataId)
            .map { fillDataDto: FillDataDto ->
                datasetBuilder.buildUnsafe(fillRequestInfo.screenInfo.authFormInfo, fillDataDto)
            }
            .doOnError { e: Throwable ->
                log("$tag: Error while building unsafe dataset: $e", e)
            }
    }
}