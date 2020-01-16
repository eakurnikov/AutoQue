package com.eakurnikov.autoque.data.db.dao.main

import androidx.room.Dao
import androidx.room.Query
import com.eakurnikov.autoque.data.db.AutofillDatabaseNames.ACCOUNTS_TABLE_NAME
import com.eakurnikov.autoque.data.db.AutofillDatabaseNames.LOGINS_TABLE_NAME
import com.eakurnikov.autoque.data.db.entity.AccountEntity
import com.eakurnikov.autoque.data.db.entity.LoginEntity
import io.reactivex.Single

/**
 * Created by eakurnikov on 2019-10-05
 */
@Dao
interface MainDao {

    @Query("SELECT * FROM $ACCOUNTS_TABLE_NAME")
    fun getAccounts(): Single<List<AccountEntity>>

    @Query("SELECT * FROM $LOGINS_TABLE_NAME WHERE accountId = :accountEntityId")
    fun getLoginsForAccount(accountEntityId: Long): Single<List<LoginEntity>>
}