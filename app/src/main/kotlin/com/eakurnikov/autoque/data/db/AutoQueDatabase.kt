package com.eakurnikov.autoque.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eakurnikov.autoque.data.db.dao.CredentialsDao
import com.eakurnikov.autoque.data.db.entity.AccountEntity
import com.eakurnikov.autoque.data.db.entity.LoginEntity

/**
 * Created by eakurnikov on 2019-09-15
 */
@Database(
    entities = [
        AccountEntity::class,
        LoginEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AutoQueDatabase : RoomDatabase() {

    abstract fun credentialsDao(): CredentialsDao
}