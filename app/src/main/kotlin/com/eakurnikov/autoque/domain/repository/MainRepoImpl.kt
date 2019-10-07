package com.eakurnikov.autoque.domain.repository

import com.eakurnikov.autoque.autofill.impl.data.Resource
import com.eakurnikov.autoque.data.dao.main.MainDao
import com.eakurnikov.autoque.data.entity.AccountRoomEntity
import com.eakurnikov.autoque.data.repository.MainRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-10-05
 */
class MainRepoImpl
@Inject constructor(
    private val dao: MainDao
) : MainRepo {

    private val accountsSubject: BehaviorSubject<Resource<List<AccountRoomEntity>>> = BehaviorSubject.create()

    private var disposable: Disposable? = null

    override fun getAccounts(): BehaviorSubject<Resource<List<AccountRoomEntity>>> {
        dispose()

        disposable = dao
            .getAccounts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { accountEntities: List<AccountRoomEntity> ->
                    accountsSubject.onNext(
                        Resource.Success(accountEntities)
                    )
                    dispose()
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