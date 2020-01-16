package com.eakurnikov.autoque.autofill.api.dependencies.data.dao

import com.eakurnikov.autoque.autofill.api.dependencies.data.model.Account
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.Login
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by eakurnikov on 2019-09-14
 */
interface AutofillDao {

    fun getAccounts(): Single<List<Account>>

    fun getAccountById(accountId: Long): Single<Account>

    fun getLoginsForAccount(accountId: Long): Single<List<Login>>

    fun getLoginById(loginId: Long): Single<Login>

    fun addAccountWithLogin(account: Account, login: Login): Completable

    fun addLoginToAccount(account: Account, login: Login): Completable

    fun updateLoginInAccount(account: Account, login: Login): Completable
}