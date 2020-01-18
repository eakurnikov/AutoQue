package com.eakurnikov.autoque.data.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.Account
import com.eakurnikov.autoque.data.db.AutofillDatabaseNames.ACCOUNTS_TABLE_NAME

/**
 * Created by eakurnikov on 2019-09-15
 */
@Entity(
    tableName = ACCOUNTS_TABLE_NAME,
    indices = [Index("id", unique = true)]
)
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    override var id: Long?,
    override val name: String,
    override val comment: String?,
    override val packageName: String
) : Account