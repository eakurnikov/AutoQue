package com.eakurnikov.autoque.viewmodel

import com.eakurnikov.common.data.Resource
import com.eakurnikov.autoque.data.db.entity.LoginEntity
import com.eakurnikov.autoque.data.repository.MainRepo
import com.eakurnikov.autoque.viewmodel.base.BaseViewModel
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-10-05
 */
class MainViewModel @Inject constructor(
    private val mainRepo: MainRepo
) : BaseViewModel() {

    var accountsSubject: BehaviorSubject<Resource<List<LoginEntity>>> =
        BehaviorSubject.createDefault(Resource.Loading(listOf()))

    override fun subscribe() {
        subscribe(
            mainRepo
                .getAccounts()
                .subscribe(
                    { resource: Resource<List<LoginEntity>> ->
                        accountsSubject.onNext(resource)
                    },
                    { error: Throwable ->
                        accountsSubject.onNext(
                            Resource.Error(error.message ?: "Logical error in db")
                        )
                    }
                )
        )
    }
}