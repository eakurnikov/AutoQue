package com.eakurnikov.autoque.autofill.impl.internal.domain.usecase.save

import android.content.IntentSender
import android.os.Bundle
import com.eakurnikov.autoque.autofill.api.dependencies.domain.auth.AutofillAuthProvider
import com.eakurnikov.autoque.autofill.impl.internal.data.enums.IntentSenderType
import com.eakurnikov.autoque.autofill.impl.internal.data.model.IntentSenderResource
import com.eakurnikov.autoque.autofill.impl.internal.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.internal.data.repositories.AutofillRepository
import com.eakurnikov.autoque.autofill.impl.internal.domain.filldata.FillDataBuilder
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-09
 */
class UpdateFillDataUseCase @Inject constructor(
    private val autofillRepo: AutofillRepository,
    private val authProvider: AutofillAuthProvider<*>,
    private val fillDataBuilder: FillDataBuilder
) {
    operator fun invoke(
        saveRequestInfo: RequestInfo,
        clientState: Bundle
    ): Maybe<IntentSenderResource> {

        return if (authProvider.isAuthRequired) {
            val authIntentSender: IntentSender = authProvider.getUpdateAuthIntentSender(clientState)
            val resource = IntentSenderResource(authIntentSender, IntentSenderType.AUTH)
            Maybe.just(resource)
        } else {
            autofillRepo
                .updateFillData(fillDataBuilder.build(saveRequestInfo))
                .toMaybe<IntentSenderResource>()
        }
    }
}