package com.eakurnikov.autoque.data.db.dao.autofill

import com.eakurnikov.autoque.autofill.api.dependencies.data.dao.AutofillDao
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.Account
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.Login
import com.eakurnikov.autoque.data.db.entity.AccountEntity
import com.eakurnikov.autoque.data.db.entity.LoginEntity
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by eakurnikov on 2019-09-15
 */
class AutofillDaoAdapter(
    private val dao: AutofillRoomDao
) : AutofillDao {

    override fun getAccounts(): Single<List<Account>> {
        return dao
            .getAccounts()
            .map { it.map { it as Account } }
    }

    override fun getAccountById(accountId: Long): Single<Account> {
        return dao
            .getAccountById(accountId)
            .map { it as Account }
    }

    override fun getLoginsForAccount(accountId: Long): Single<List<Login>> {
        return dao
            .getLoginsForAccount(accountId)
            .map { it.map { it as Login } }
    }

    override fun getLoginById(loginId: Long): Single<Login> {
        return dao
            .getLoginById(loginId)
            .map { it as Login }
    }

    override fun addAccountWithLogin(account: Account, login: Login): Completable {
        var loginRowId: Long = 0
        val accountRowId = addAccount(account)

        if (accountRowId > 0) {
            loginRowId = addLogin(accountRowId, login)
        }

        return if (accountRowId > 0 && loginRowId > 0) {
            Completable.complete()
        } else {
            Completable.error(RuntimeException("Error while saving credentials"))
        }
    }

    override fun addLoginToAccount(account: Account, login: Login): Completable {
        val loginRowId: Long = addLogin(account.id!!, login)

        return if (loginRowId > 0) {
            Completable.complete()
        } else {
            Completable.error(RuntimeException("Error while adding login to account"))
        }
    }

    override fun updateLoginInAccount(account: Account, login: Login): Completable {
        val updatedLoginEntitiesCount: Int = updateLogin(account.id!!, login)

        return if (updatedLoginEntitiesCount > 0) {
            Completable.complete()
        } else {
            Completable.error(RuntimeException("Error while adding login to account"))
        }
    }

    private fun addAccount(account: Account): Long {
        return dao.createAccount(
            when (account) {
                is AccountEntity -> account
                else -> with(account) { AccountEntity(id, name, comment, packageName) }
            }
        )
    }

    private fun addLogin(accountId: Long, login: Login): Long {
        return dao.addLoginToAccount(
            when (login) {
                is LoginEntity -> login
                else -> with(login) { LoginEntity(id, accountId, login.login, password, comment) }
            }
        )
    }

    private fun updateLogin(accountId: Long, login: Login): Int {
        return dao.updateLogin(
            when (login) {
                is LoginEntity -> login
                else -> with(login) { LoginEntity(id, accountId, login.login, password, comment) }
            }
        )
    }
}