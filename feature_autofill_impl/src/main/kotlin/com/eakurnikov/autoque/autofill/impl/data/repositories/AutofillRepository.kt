package com.eakurnikov.autoque.autofill.impl.data.repositories

import com.eakurnikov.autoque.autofill.impl.data.model.FillDataEntity
import com.eakurnikov.autoque.autofill.impl.util.FillDataResource
import com.eakurnikov.autoque.autofill.impl.util.SaveResource
import io.reactivex.subjects.PublishSubject

/**
 * Created by eakurnikov on 2019-09-15
 */
interface AutofillRepository {

    fun getDatasets(clientPackageName: String): PublishSubject<FillDataResource>

    fun saveDatasets(fillDataEntity: FillDataEntity): PublishSubject<SaveResource>

    fun unsubscribe()
}