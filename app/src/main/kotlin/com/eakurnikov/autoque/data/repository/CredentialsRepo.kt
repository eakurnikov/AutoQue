package com.eakurnikov.autoque.data.repository

import com.eakurnikov.common.data.Resource
import com.eakurnikov.autoque.data.model.Credentials
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by eakurnikov on 2019-10-05
 */
interface CredentialsRepo {

    val credentialsSubject: BehaviorSubject<Resource<List<Credentials>>>

    fun getCredentials()

    fun loadCredentials()
}