package com.eakurnikov.autoque.autofill.impl.internal.data.repositories

import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataDto
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataId
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by eakurnikov on 2019-09-15
 */
interface AutofillRepository {

    fun getFillData(packageName: String): Single<List<FillDataDto>>

    fun getFillDataById(fillDataId: FillDataId): Single<FillDataDto>

    fun addFillData(fillDataDto: FillDataDto): Completable

    fun updateFillData(fillDataDto: FillDataDto): Completable
}