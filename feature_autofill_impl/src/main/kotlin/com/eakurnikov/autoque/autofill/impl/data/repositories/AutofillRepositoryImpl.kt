package com.eakurnikov.autoque.autofill.impl.data.repositories

import com.eakurnikov.autoque.autofill.api.dependencies.data.dao.AutofillDao
import com.eakurnikov.autoque.autofill.api.dependencies.data.entity.AccountEntity
import com.eakurnikov.autoque.autofill.api.dependencies.data.entity.LoginEntity
import com.eakurnikov.autoque.autofill.impl.data.Resource
import com.eakurnikov.autoque.autofill.impl.data.model.FillDataEntity
import com.eakurnikov.autoque.autofill.impl.extensions.sort
import com.eakurnikov.autoque.autofill.impl.extensions.truncate
import com.eakurnikov.autoque.autofill.impl.util.FillDataResource
import com.eakurnikov.autoque.autofill.impl.util.SaveResource
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 */
class AutofillRepositoryImpl
@Inject constructor(
    private val autofillDao: AutofillDao
) : AutofillRepository {

    private companion object {
        private const val FILL_DATA_ENTITIES_LIMIT: Int = 20
    }

    private val fillDataResourceSubject: PublishSubject<FillDataResource> = PublishSubject.create()
    private val saveResourceSubject: PublishSubject<SaveResource> = PublishSubject.create()
    private var disposables: CompositeDisposable = CompositeDisposable()

    override fun getDatasets(clientPackageName: String): PublishSubject<FillDataResource> {
        disposables.add(
            autofillDao
                .getAccounts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapPublisher { Flowable.fromIterable(it) }
                .flatMapSingle { accountEntity: AccountEntity ->
                    Single.zip(
                        Single.just(accountEntity),
                        autofillDao.getLoginsForAccount(accountEntity.id!!),
                        BiFunction { acc: AccountEntity, logins: List<LoginEntity> ->
                            logins.map { FillDataEntity(acc, it) }
                        }
                    )
                }
                .flatMapIterable { it }
                .toList()
                .subscribe(
                    { fillDataEntities: List<FillDataEntity> ->
                        val processedFillDataEntities = fillDataEntities
                            .sort(clientPackageName)
                            .truncate(FILL_DATA_ENTITIES_LIMIT)

                        fillDataResourceSubject.onNext(
                            Resource.Success(processedFillDataEntities)
                        )
                    },
                    { error: Throwable ->
                        fillDataResourceSubject.onNext(
                            Resource.Error(error.message ?: "An error occurred")
                        )
                    }
                )
        )

        return fillDataResourceSubject
    }

    override fun saveDatasets(fillDataEntity: FillDataEntity): PublishSubject<SaveResource> {
        disposables.add(
            autofillDao
                .addAccountWithLogin(fillDataEntity.accountEntity, fillDataEntity.loginEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { saveResourceSubject.onNext(Resource.Success(Unit)) },
                    { error: Throwable ->
                        saveResourceSubject.onNext(
                            Resource.Error(error.message ?: "An error occurred")
                        )
                    }
                )
        )

        return saveResourceSubject
    }

    override fun unsubscribe() {
        disposables.clear()
    }
}