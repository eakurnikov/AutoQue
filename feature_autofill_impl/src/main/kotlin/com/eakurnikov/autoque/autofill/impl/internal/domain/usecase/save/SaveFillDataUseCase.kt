package com.eakurnikov.autoque.autofill.impl.internal.domain.usecase.save

import android.content.IntentSender
import android.os.Bundle
import com.eakurnikov.autoque.autofill.api.dependencies.domain.auth.AutofillAuthProvider
import com.eakurnikov.autoque.autofill.impl.internal.data.enums.IntentSenderType
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataDto
import com.eakurnikov.autoque.autofill.impl.internal.data.model.IntentSenderResource
import com.eakurnikov.autoque.autofill.impl.internal.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.internal.data.repositories.AutofillRepository
import com.eakurnikov.autoque.autofill.impl.internal.domain.filldata.FillDataBuilder
import com.eakurnikov.autoque.autofill.impl.internal.domain.providers.update.UpdatePromptProvider
import com.eakurnikov.autoque.autofill.impl.internal.extensions.log
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-09
 */
class SaveFillDataUseCase @Inject constructor(
    private val autofillRepo: AutofillRepository,
    private val authProvider: AutofillAuthProvider<*>,
    private val updatePromptProvider: UpdatePromptProvider,
    private val fillDataBuilder: FillDataBuilder
) {
    private val tag: String = "SaveFillDataUseCase"

    operator fun invoke(
        saveRequestInfo: RequestInfo,
        clientState: Bundle
    ): Maybe<IntentSenderResource> {

        return if (authProvider.isAuthRequired) {
            val authIntentSender: IntentSender = authProvider.getSaveAuthIntentSender(clientState)
            val resource = IntentSenderResource(authIntentSender, IntentSenderType.AUTH)
            Maybe.just(resource)
        } else {
            autofillRepo
                .getAllFillData(saveRequestInfo.clientPackageName)
                .flatMapMaybe { existingDtos: List<FillDataDto> ->
                    val newDto: FillDataDto = fillDataBuilder.build(saveRequestInfo)

                    val withSameLogin: List<FillDataDto> =
                        existingDtos.filter { existingDto: FillDataDto ->
                            existingDto.account.packageName == newDto.account.packageName &&
                                    existingDto.login.login == newDto.login.login
                        }
                    when {
                        withSameLogin.isEmpty() -> {
                            autofillRepo
                                .addFillData(newDto)
                                .toMaybe<IntentSenderResource>()
                        }
                        else -> {
                            val promptIntentSender: IntentSender =
                                updatePromptProvider.getPromptIntentSender(clientState)

                            val resource = IntentSenderResource(
                                promptIntentSender,
                                IntentSenderType.PROMPT
                            )

                            Maybe.just(resource)
                        }
                    }
                }
                .doOnError { e: Throwable ->
                    log("$tag: Error while flat mapping: $e", e)
                }
        }
    }
}