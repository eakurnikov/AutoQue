package com.eakurnikov.autoque.data.dao.main

import androidx.room.Dao
import androidx.room.Query
import com.eakurnikov.autoque.data.AutofillDatabaseNames
import com.eakurnikov.autoque.data.AutofillDatabaseNames.ACCOUNTS_TABLE_NAME
import com.eakurnikov.autoque.data.entity.AccountRoomEntity
import com.eakurnikov.autoque.data.entity.LoginRoomEntity
import io.reactivex.Single

/**
 * Created by eakurnikov on 2019-10-05
 */
@Dao
interface MainDao {

    @Query("SELECT * FROM $ACCOUNTS_TABLE_NAME")
    fun getAccounts(): Single<List<AccountRoomEntity>>

    @Query("SELECT * FROM ${AutofillDatabaseNames.LOGINS_TABLE_NAME} WHERE accountId = :accountEntityId")
    fun getLoginsForAccount(accountEntityId: Long): Single<List<LoginRoomEntity>>
}