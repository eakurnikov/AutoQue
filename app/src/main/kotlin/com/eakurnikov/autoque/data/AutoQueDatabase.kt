package com.eakurnikov.autoque.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eakurnikov.autoque.data.dao.autofill.AutofillRoomDao
import com.eakurnikov.autoque.data.dao.main.MainDao
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
abstract class AutoQueDatabase : RoomDatabase() {

    abstract fun autofillDao(): AutofillRoomDao

    abstract fun mainDao(): MainDao
}