package com.eakurnikov.autoque.autofill.impl.domain.request.save

import com.eakurnikov.autoque.autofill.impl.data.model.FillDataEntity
import com.eakurnikov.autoque.autofill.impl.data.model.RequestInfo
import com.eakurnikov.autoque.autofill.impl.data.repositories.AutofillRepository
import com.eakurnikov.autoque.autofill.impl.util.SaveResource
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 *
 * Saves filled user data via [AutofillRepository] and notifies subscribers via
 * [saveFillDataSubject]. Used in [SaveRequestHandler] and in auth activity.
 */
class FillDataSaver @Inject constructor(
    private val autofillRepo: AutofillRepository,
    private val fillDataBuilder: FillDataBuilder
) {
    private val saveFillDataSubject: PublishSubject<SaveResource> = PublishSubject.create()
    private var disposable: Disposable? = null

    fun saveFillData(requestInfo: RequestInfo): PublishSubject<SaveResource> {
        val fillDataEntity: FillDataEntity = fillDataBuilder.build(requestInfo)

        disposable = autofillRepo
            .saveDatasets(fillDataEntity)
            .subscribe(
                { saveResource: SaveResource ->
                    saveFillDataSubject.onNext(saveResource)
                    unsubscribe()
                },
                { error: Throwable ->
                    saveFillDataSubject.onError(error)
                    unsubscribe()
                }
            )

        return saveFillDataSubject
    }

    private fun unsubscribe() {
        autofillRepo.unsubscribe()
        disposable?.dispose()
        disposable = null
    }
}