package com.eakurnikov.autoque.data.dao.main

import androidx.room.Dao
import androidx.room.Query
import com.eakurnikov.autoque.data.AutofillDatabaseNames.ACCOUNTS_TABLE_NAME
import com.eakurnikov.autoque.data.entity.AccountRoomEntity
import io.reactivex.Single

/**
 * Created by eakurnikov on 2019-10-05
 */
@Dao
interface MainDao {

    @Query("SELECT * FROM $ACCOUNTS_TABLE_NAME")
    fun getAccounts(): Single<List<AccountRoomEntity>>
}