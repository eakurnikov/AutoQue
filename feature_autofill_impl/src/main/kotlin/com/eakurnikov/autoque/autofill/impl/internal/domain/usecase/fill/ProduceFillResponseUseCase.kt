package com.eakurnikov.autoque.autofill.impl.internal.domain.usecase.fill

import android.content.IntentSender
import android.os.Bundle
import android.service.autofill.FillResponse
import com.eakurnikov.autoque.autofill.api.dependencies.domain.auth.AutofillAuthProvider
import com.eakurnikov.autoque.autofill.impl.internal.data.enums.FillResponseType
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataDto
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillResponseResource
import com.eakurnikov.autoque.autofill.impl.internal.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.internal.data.repositories.AutofillRepository
import com.eakurnikov.autoque.autofill.impl.internal.domain.response.FillResponseBuilder
import com.eakurnikov.autoque.autofill.impl.internal.extensions.log
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-09
 */
class ProduceFillResponseUseCase @Inject constructor(
    private val autofillRepo: AutofillRepository,
    private val authProvider: AutofillAuthProvider<*>,
    private val fillResponseBuilder: FillResponseBuilder
) {
    private val tag: String = "ProduceFillResponseUseCase"

    operator fun invoke(
        fillRequestInfo: RequestInfo,
        clientState: Bundle
    ): Single<FillResponseResource> {

        return if (authProvider.isAuthRequired) {
            val authIntentSender: IntentSender = authProvider.getFillAuthIntentSender(clientState)

            val lockedResponse: FillResponse =
                fillResponseBuilder.buildLocked(authIntentSender, fillRequestInfo, clientState)

            val resource = FillResponseResource(lockedResponse, FillResponseType.LOCKED)

            Single.just(resource)
        } else {
            val clientPackageName: String = fillRequestInfo.clientPackageName

            autofillRepo
                .getFillData(clientPackageName)
                .map { fillDataDtos: List<FillDataDto> ->
                    when {
                        fillDataDtos.isEmpty() -> {
                            val emptyResponse: FillResponse =
                                fillResponseBuilder.buildEmpty(fillRequestInfo, clientState)
                            FillResponseResource(emptyResponse, FillResponseType.EMPTY)
                        }
                        else -> {
                            val unlockedResponse: FillResponse =
                                fillResponseBuilder.buildUnlocked(
                                    fillDataDtos,
                                    fillRequestInfo,
                                    clientState
                                )
                            FillResponseResource(unlockedResponse, FillResponseType.UNLOCKED)
                        }
                    }
                }
                .doOnError { e: Throwable ->
                    log("$tag: Error while building fill response: $e", e)
                }
        }
    }
}