package com.eakurnikov.autoque.autofill.impl.internal.data.repositories

import com.eakurnikov.autoque.autofill.api.dependencies.data.dao.AutofillDao
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.Account
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.Login
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataDto
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataId
import com.eakurnikov.autoque.autofill.impl.internal.extensions.log
import com.eakurnikov.autoque.autofill.impl.internal.extensions.rankByPackageName
import com.eakurnikov.autoque.autofill.impl.internal.extensions.truncate
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 */
class AutofillRepositoryImpl @Inject constructor(
    private val autofillDao: AutofillDao
) : AutofillRepository {

    private val tag: String = "AutofillRepository"
    private val fillDataDtosLimit: Int = 20

    override fun getFillData(packageName: String): Single<List<FillDataDto>> {
        return getAccounts()
            .flatMapPublisher { accounts: List<Account> ->
                Flowable.fromIterable(accounts)
            }
            .flatMapSingle { account: Account ->
                Single.zip(
                    Single.just(account),
                    autofillDao.getLoginsForAccount(account.id!!),
                    BiFunction { acc: Account, logins: List<Login> ->
                        logins.map { FillDataDto(acc, it) }
                    }
                )
            }
            .flatMapIterable { fillDataDtos: List<FillDataDto> ->
                fillDataDtos
            }
            .toList()
            .map { fillDataDtos: List<FillDataDto> ->
                fillDataDtos.rankByPackageName(packageName).truncate(fillDataDtosLimit)
            }
            .doOnError { e: Throwable ->
                log("$tag: Error while getting fill data: $e. Emit empty list", e)
            }
            .onErrorReturn {
                emptyList()
            }
    }

    override fun getFillDataById(fillDataId: FillDataId): Single<FillDataDto> {
        return Single.zip(
            autofillDao.getAccountById(fillDataId.accountId!!),
            autofillDao.getLoginById(fillDataId.loginId!!),
            BiFunction { account: Account, login: Login ->
                FillDataDto(account, login)
            }
        ).doOnError { e: Throwable ->
            log("$tag: Error while getting fill data by id: $e", e)
        }.onErrorResumeNext { e: Throwable ->
            Single.error(e)
        }
    }

    override fun addFillData(fillDataDto: FillDataDto): Completable {
        return getAccounts()
            .flatMapCompletable { accounts: List<Account> ->
                val withSamePackageName: List<Account> =
                    accounts.filter { account: Account ->
                        account.packageName == fillDataDto.account.packageName
                    }

                if (withSamePackageName.isEmpty()) {
                    autofillDao.addAccountWithLogin(fillDataDto.account, fillDataDto.login)
                } else {
                    autofillDao.addLoginToAccount(withSamePackageName.first(), fillDataDto.login)
                }
            }
            .doOnError { e: Throwable ->
                log("$tag: Error while saving fill data: $e", e)
            }
            .onErrorResumeNext { e: Throwable ->
                Completable.error(e)
            }
    }

    override fun updateFillData(fillDataDto: FillDataDto): Completable {
        return autofillDao
            .updateLoginInAccount(fillDataDto.account, fillDataDto.login)
            .doOnError { e: Throwable ->
                log("$tag: Error while updating fill data: $e", e)
            }
    }

    private fun getAccounts(): Single<List<Account>> {
        return autofillDao
            .getAccounts()
            .doOnError { e: Throwable ->
                log("$tag: Error while getting accounts: $e. Emit empty list", e)
            }
            .onErrorReturn {
                emptyList()
            }
    }
}