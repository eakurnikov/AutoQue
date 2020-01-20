package com.eakurnikov.autoque.domain.repository

import android.util.Log
import com.eakurnikov.autoque.data.db.dao.CredentialsDao
import com.eakurnikov.autoque.data.db.entity.AccountEntity
import com.eakurnikov.common.data.Resource
import com.eakurnikov.autoque.data.db.entity.LoginEntity
import com.eakurnikov.autoque.data.model.Credentials
import com.eakurnikov.autoque.data.network.api.CredentialsApi
import com.eakurnikov.autoque.data.network.dto.CredentialsDto
import com.eakurnikov.autoque.data.network.dto.ResponseDto
import com.eakurnikov.autoque.data.repository.CredentialsRepo
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-10-05
 */
class CredentialsRepoImpl @Inject constructor(
    private val api: CredentialsApi,
    private val dao: CredentialsDao
) : CredentialsRepo {

    private val tag: String = "CredentialsRepo"

    private var apiDisposable: Disposable? = null
    private var daoDisposable: Disposable? = null

    override val credentialsSubject: BehaviorSubject<Resource<List<Credentials>>> =
        BehaviorSubject.create()

    override fun getCredentials() {
        credentialsSubject.onNext(Resource.Loading())
        disposeDao()
        daoDisposable = dao.getAccounts()
            .flatMapPublisher { accountEntities: List<AccountEntity> ->
                Flowable.fromIterable(accountEntities)
            }
            .flatMapSingle { accountEntity: AccountEntity ->
                Single.zip(
                    Single.just(accountEntity),
                    dao.getLoginsForAccount(accountEntity.id!!),
                    BiFunction { acc: AccountEntity, loginEntities: List<LoginEntity> ->
                        loginEntities.map { Credentials(acc, it) }
                    }
                )
            }
            .doOnError { e: Throwable ->
                Log.i(tag, "Error while getting credentials: $e. Emit empty list")
                e.printStackTrace()
            }
            .onErrorReturn {
                emptyList()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { credentialsList: List<Credentials> ->
                    credentialsSubject.onNext(
                        Resource.Success(credentialsList.takeLast(2))
                    )
                    loadCredentials()
                },
                { error: Throwable ->
                    credentialsSubject.onNext(
                        Resource.Error(error.message ?: "Logical error in db")
                    )
                    disposeDao()
                }
            )
    }

    override fun loadCredentials() {
        credentialsSubject.onNext(Resource.Loading())
        disposeApi()
        apiDisposable = api.getCredentials()
            .flatMapPublisher { responseDto: ResponseDto ->
                Flowable.fromIterable(responseDto.result)
            }
            .flatMap(
                { credentialsDto: CredentialsDto ->
                    AccountEntity(credentialsDto)
                        .also { it.id = dao.createAccount(it) }
                        .let { Flowable.just(it) }
                },
                { credentialsDto: CredentialsDto, accountEntity: AccountEntity ->
                    LoginEntity(accountEntity.id!!, credentialsDto)
                        .also { it.id = dao.addLoginToAccount(it) }
                        .let { Credentials(accountEntity, it) }
                }
            )
            .toList()
            .doOnError { e: Throwable ->
                Log.i(tag, "Error while loading credentials: $e. Emit empty list")
                e.printStackTrace()
            }
            .onErrorReturn {
                emptyList()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { credentialsList: List<Credentials> ->
                    credentialsSubject.onNext(
                        Resource.Success(credentialsList)
                    )
                },
                { error: Throwable ->
                    credentialsSubject.onNext(
                        Resource.Error(error.message ?: "Logical error in db")
                    )
                    disposeApi()
                }
            )
    }

    private fun disposeApi() {
        apiDisposable?.dispose()
        apiDisposable = null
    }

    private fun disposeDao() {
        daoDisposable?.dispose()
        daoDisposable = null
    }
}