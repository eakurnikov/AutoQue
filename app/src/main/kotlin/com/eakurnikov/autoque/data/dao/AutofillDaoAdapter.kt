package com.eakurnikov.autoque.data.dao

import com.eakurnikov.autoque.autofill.api.dependencies.data.dao.AutofillDao
import com.eakurnikov.autoque.autofill.api.dependencies.data.entity.AccountEntity
import com.eakurnikov.autoque.autofill.api.dependencies.data.entity.LoginEntity
import com.eakurnikov.autoque.data.entity.AccountRoomEntity
import com.eakurnikov.autoque.data.entity.LoginRoomEntity
import com.eakurnikov.autoque.util.ListSingleTransformer
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by eakurnikov on 2019-09-15
 */
class AutofillDaoAdapter(
    private val dao: AutofillRoomDao
) : AutofillDao {

    override fun getAccounts(): Single<List<AccountEntity>> {
        return dao
            .getAccounts()
            .compose(ListSingleTransformer { it })
    }

    override fun getLoginsForAccount(accountId: Long): Single<List<LoginEntity>> {
        return dao
            .getLoginsForAccount(accountId)
            .compose(ListSingleTransformer { it })
    }

    override fun addAccountWithLogin(accountEntity: AccountEntity, loginEntity: LoginEntity): Completable {
        var loginRowId: Long = 0

        val accountRoomEntity: AccountRoomEntity =
            when (accountEntity) {
                is AccountRoomEntity -> accountEntity
                else -> with(accountEntity) { AccountRoomEntity(id, name, comment, packageName) }
            }

        val accountRowId = dao.createAccount(accountRoomEntity)

        if (accountRowId > 0) {
            val loginRoomEntity: LoginRoomEntity =
                when (loginEntity) {
                    is LoginRoomEntity -> loginEntity
                    else -> with(loginEntity) { LoginRoomEntity(id, accountRowId, name, login, password, comment) }
                }

            loginRowId = dao.addLoginToAccount(loginRoomEntity)
        }

        return if (accountRowId > 0 && loginRowId > 0)
            Completable.complete()
        else
            Completable.error(RuntimeException("Error while saving credentials"))
    }
}