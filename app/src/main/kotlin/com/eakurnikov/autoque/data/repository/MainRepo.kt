package com.eakurnikov.autoque.data.repository

import com.eakurnikov.common.data.Resource
import com.eakurnikov.autoque.data.db.entity.LoginEntity
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by eakurnikov on 2019-10-05
 */
interface MainRepo {

    fun getAccounts(): BehaviorSubject<Resource<List<LoginEntity>>>
}