package com.eakurnikov.autoque.autofill.impl.internal.domain.usecase.save

import com.eakurnikov.autoque.autofill.impl.internal.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.internal.data.repositories.AutofillRepository
import com.eakurnikov.autoque.autofill.impl.internal.domain.filldata.FillDataBuilder
import io.reactivex.Completable
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-09
 */
class UpdateFillDataUseCase @Inject constructor(
    private val autofillRepo: AutofillRepository,
    private val fillDataBuilder: FillDataBuilder
) {
    operator fun invoke(saveRequestInfo: RequestInfo): Completable {
        return autofillRepo.updateFillData(fillDataBuilder.build(saveRequestInfo))
    }
}