package com.eakurnikov.autoque.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eakurnikov.autoque.data.dao.AutofillRoomDao
import com.eakurnikov.autoque.data.entity.AccountRoomEntity
import com.eakurnikov.autoque.data.entity.LoginRoomEntity

/**
 * Created by eakurnikov on 2019-09-15
 */
@Database(
    entities = [
        AccountRoomEntity::class,
        LoginRoomEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AutofillDatabase : RoomDatabase() {

    abstract fun autofillDao(): AutofillRoomDao
}