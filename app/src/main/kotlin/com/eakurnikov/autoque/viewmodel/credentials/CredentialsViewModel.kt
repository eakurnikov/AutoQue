package com.eakurnikov.autoque.viewmodel.credentials

import android.net.Uri
import com.eakurnikov.common.data.Resource
import com.eakurnikov.autoque.data.model.Credentials
import com.eakurnikov.autoque.data.repository.CredentialsRepo
import com.eakurnikov.autoque.domain.usecase.ImportPasswordsUseCase
import com.eakurnikov.autoque.viewmodel.base.BaseViewModel
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-12-15
 */
class CredentialsViewModel @Inject constructor(
    private val repo: CredentialsRepo,
    private val importPasswordsUseCase: ImportPasswordsUseCase
) : BaseViewModel() {

    var credentialsSubject: BehaviorSubject<Resource<List<Credentials>>> =
        BehaviorSubject.createDefault(Resource.Loading(listOf()))

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

    fun onCredentialsCanBeLoaded() {
        repo.getCredentials()
    }

    fun onPasswordsImport(uri: Uri) {
        subscribe(
            importPasswordsUseCase
                .invoke(uri)
                .subscribe(
                    { credentialsList: List<Credentials> ->
                        credentialsSubject.onNext(
                            Resource.Success(credentialsList)
                        )
                    }, { error: Throwable ->
                        credentialsSubject.onNext(
                            Resource.Error(error.message ?: "Error while passwords import")
                        )
                    }
                )
        )
    }
}