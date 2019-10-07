package com.eakurnikov.autoque.data.repository

import com.eakurnikov.autoque.autofill.impl.data.Resource
import com.eakurnikov.autoque.data.entity.AccountRoomEntity
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by eakurnikov on 2019-10-05
 */
interface MainRepo {

    fun getAccounts(): BehaviorSubject<Resource<List<AccountRoomEntity>>>
}