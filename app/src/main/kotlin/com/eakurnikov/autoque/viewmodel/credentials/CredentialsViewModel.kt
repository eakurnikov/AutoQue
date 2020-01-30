package com.eakurnikov.autoque.viewmodel.credentials

import com.eakurnikov.common.data.Resource
import com.eakurnikov.autoque.data.model.Credentials
import com.eakurnikov.autoque.data.repository.CredentialsRepo
import com.eakurnikov.autoque.viewmodel.base.BaseViewModel
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-12-15
 */
class CredentialsViewModel @Inject constructor(
    private val repo: CredentialsRepo
) : BaseViewModel() {

    var credentialsSubject: BehaviorSubject<Resource<List<Credentials>>> =
        BehaviorSubject.createDefault(Resource.Loading(listOf()))

    override fun onStart() {
        super.onStart()
        repo.getCredentials()
    }

    override fun subscribe() {
        subscribe(
            repo.credentialsSubject.subscribe(
                { resource: Resource<List<Credentials>> ->
                    credentialsSubject.onNext(resource)
                },
                { error: Throwable ->
                    credentialsSubject.onNext(
                        Resource.Error(error.message ?: "Logical error in db")
                    )
                }
            )
        )
    }

    fun onRefresh() {
        repo.loadCredentials()
    }
}