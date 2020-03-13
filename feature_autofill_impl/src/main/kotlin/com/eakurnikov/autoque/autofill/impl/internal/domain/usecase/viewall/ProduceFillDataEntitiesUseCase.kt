package com.eakurnikov.autoque.autofill.impl.internal.domain.usecase.viewall

import android.os.Bundle
import com.eakurnikov.autoque.autofill.api.dependencies.domain.auth.AutofillAuthProvider
import com.eakurnikov.autoque.autofill.impl.internal.data.enums.FillDataType
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataDto
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataResource
import com.eakurnikov.autoque.autofill.impl.internal.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.internal.data.repositories.AutofillRepository
import com.eakurnikov.autoque.autofill.impl.internal.extensions.log
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-16
 */
class ProduceFillDataEntitiesUseCase @Inject constructor(
    private val autofillRepo: AutofillRepository,
    private val authProvider: AutofillAuthProvider<*>
) {
    private val tag: String = "ProduceFillDataEntitiesUseCase"

    operator fun invoke(
        viewAllRequestInfo: RequestInfo,
        clientState: Bundle
    ): Single<FillDataResource> {

        return if (authProvider.isAuthRequired) {
            Single.just(
                FillDataResource(
                    null,
                    authProvider.getViewAllAuthIntentSender(clientState),
                    FillDataType.LOCKED
                )
            )
        } else {
            autofillRepo
                .getAllFillData(viewAllRequestInfo.clientPackageName)
                .map { dtos: List<FillDataDto> ->
                    FillDataResource(dtos, null, FillDataType.UNLOCKED)
                }
                .doOnError { e: Throwable ->
                    log("$tag: Error while building fill data: $e", e)
                }
        }
    }
}