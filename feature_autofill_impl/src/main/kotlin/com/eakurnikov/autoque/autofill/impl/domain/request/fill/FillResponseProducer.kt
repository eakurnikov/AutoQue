package com.eakurnikov.autoque.autofill.impl.domain.request.fill

import android.content.IntentSender
import android.os.Bundle
import android.service.autofill.FillResponse
import com.eakurnikov.autoque.autofill.impl.data.Resource
import com.eakurnikov.autoque.autofill.impl.data.repositories.AutofillRepository
import com.eakurnikov.autoque.autofill.impl.util.FillDataResource
import com.eakurnikov.autoque.autofill.impl.util.FillResponseResource
import com.eakurnikov.autoque.autofill.impl.extensions.getRequestInfo
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 *
 * Produces locked and unlocked [FillResponse]s built via [FillResponseBuilder] using fetched data
 * from [AutofillRepository] and notifies subscribers via [unlockedFillResponseResourceSubject].
 */
class FillResponseProducer @Inject constructor(
    private val autofillRepo: AutofillRepository,
    private val fillResponseBuilder: FillResponseBuilder
) {
    private val unlockedFillResponseResourceSubject: PublishSubject<FillResponseResource> = PublishSubject.create()
    private var disposable: Disposable? = null

    fun produceLockedFillResponse(authIntentSender: IntentSender, clientState: Bundle): FillResponse =
        fillResponseBuilder.buildLocked(authIntentSender, clientState)

    fun produceUnlockedFillResponse(clientState: Bundle): PublishSubject<FillResponseResource> {
        disposable = autofillRepo
            .getDatasets(clientState.getRequestInfo()!!.clientPackageName)
            .subscribe(
                { fillDataResource: FillDataResource ->
                    when (fillDataResource) {
                        is Resource.Success -> {
                            val unlockedFillResponse: FillResponse =
                                fillResponseBuilder.buildUnlocked(
                                    fillDataResource.data,
                                    clientState
                                )

                            unlockedFillResponseResourceSubject.onNext(
                                Resource.Success(unlockedFillResponse)
                            )
                            unsubscribe()
                        }
                        is Resource.Loading -> Unit
                        is Resource.Error -> {
                            unlockedFillResponseResourceSubject.onNext(
                                Resource.Error(fillDataResource.message)
                            )
                            unsubscribe()
                        }
                    }
                },
                { error: Throwable ->
                    unlockedFillResponseResourceSubject.onError(error)
                    unsubscribe()
                }
            )

        return unlockedFillResponseResourceSubject
    }

    private fun unsubscribe() {
        autofillRepo.unsubscribe()
        disposable?.dispose()
        disposable = null
    }
}