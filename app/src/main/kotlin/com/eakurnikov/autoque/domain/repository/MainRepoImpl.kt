package com.eakurnikov.autoque.domain.repository

import com.eakurnikov.common.data.Resource
import com.eakurnikov.autoque.data.db.dao.main.MainDao
import com.eakurnikov.autoque.data.db.entity.LoginEntity
import com.eakurnikov.autoque.data.repository.MainRepo
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-10-05
 */
class MainRepoImpl @Inject constructor(
    private val dao: MainDao
) : MainRepo {

    private val accountsSubject: BehaviorSubject<Resource<List<LoginEntity>>> =
        BehaviorSubject.create()

    private var disposable: Disposable? = null

    override fun getAccounts(): BehaviorSubject<Resource<List<LoginEntity>>> {
        dispose()

        disposable = dao
            .getAccounts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMapPublisher { Flowable.fromIterable(it) }
            .flatMapSingle { dao.getLoginsForAccount(it.id!!) }
            .flatMapIterable { it }
            .toList()
            .subscribe(
                { loginEntities: List<LoginEntity> ->
                    accountsSubject.onNext(
                        Resource.Success(loginEntities)
                    )
                },
                { error: Throwable ->
                    accountsSubject.onNext(
                        Resource.Error(error.message ?: "Logical error in db")
                    )
                    dispose()
                }
            )

        return accountsSubject
    }

    private fun dispose() {
        disposable?.dispose()
        disposable = null
    }
}