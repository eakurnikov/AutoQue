package com.eakurnikov.autoque.autofill.api.dependencies.data.dao

import com.eakurnikov.autoque.autofill.api.dependencies.data.entity.LoginEntity
import com.eakurnikov.autoque.autofill.api.dependencies.data.entity.AccountEntity
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by eakurnikov on 2019-09-14
 */
interface AutofillDao {

    fun getAccounts(): Single<List<AccountEntity>>

    fun getLoginsForAccount(accountId: Long): Single<List<LoginEntity>>

    fun addAccountWithLogin(accountEntity: AccountEntity, loginEntity: LoginEntity): Completable
}