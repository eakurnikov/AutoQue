package com.eakurnikov.autoque.data.db.dao

import androidx.room.*
import com.eakurnikov.autoque.data.db.AutoQueDatabaseNames.ACCOUNTS_TABLE_NAME
import com.eakurnikov.autoque.data.db.AutoQueDatabaseNames.LOGINS_TABLE_NAME
import com.eakurnikov.autoque.data.db.entity.AccountEntity
import com.eakurnikov.autoque.data.db.entity.LoginEntity
import io.reactivex.Single

/**
 * Created by eakurnikov on 2019-09-15
 */
@Dao
interface CredentialsDao {

    @Query("SELECT * FROM $ACCOUNTS_TABLE_NAME")
    fun getAccounts(): Single<List<AccountEntity>>

    @Query("SELECT * FROM $ACCOUNTS_TABLE_NAME WHERE id = :accountId")
    fun getAccountById(accountId: Long): Single<AccountEntity>

    @Query("SELECT * FROM $LOGINS_TABLE_NAME WHERE accountId = :accountId")
    fun getLoginsForAccount(accountId: Long): Single<List<LoginEntity>>

    @Query("SELECT * FROM $LOGINS_TABLE_NAME WHERE id = :loginId")
    fun getLoginById(loginId: Long): Single<LoginEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createAccount(accountEntity: AccountEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createAccountsList(accountEntities: List<AccountEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLoginToAccount(loginEntity: LoginEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLoginsToAccount(loginEntities: List<LoginEntity>): List<Long>

    @Update
    fun updateLogin(loginEntity: LoginEntity): Int
}