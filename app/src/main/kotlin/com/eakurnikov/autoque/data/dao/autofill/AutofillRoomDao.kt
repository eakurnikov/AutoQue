package com.eakurnikov.autoque.data.dao.autofill

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eakurnikov.autoque.data.AutofillDatabaseNames.ACCOUNTS_TABLE_NAME
import com.eakurnikov.autoque.data.AutofillDatabaseNames.LOGINS_TABLE_NAME
import com.eakurnikov.autoque.data.entity.AccountRoomEntity
import com.eakurnikov.autoque.data.entity.LoginRoomEntity
import io.reactivex.Single

/**
 * Created by eakurnikov on 2019-09-15
 */
@Dao
interface AutofillRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createAccount(accountEntity: AccountRoomEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLoginToAccount(loginEntity: LoginRoomEntity): Long

    @Query("SELECT * FROM $ACCOUNTS_TABLE_NAME")
    fun getAccounts(): Single<List<AccountRoomEntity>>

    @Query("SELECT * FROM $LOGINS_TABLE_NAME WHERE accountId = :accountEntityId")
    fun getLoginsForAccount(accountEntityId: Long): Single<List<LoginRoomEntity>>
}