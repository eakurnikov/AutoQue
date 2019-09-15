package com.eakurnikov.autoque.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.eakurnikov.autoque.autofill.api.dependencies.data.entity.AccountEntity
import com.eakurnikov.autoque.data.AutofillDatabaseNames.ACCOUNTS_TABLE_NAME

/**
 * Created by eakurnikov on 2019-09-15
 */
@Entity(
    tableName = ACCOUNTS_TABLE_NAME,
    indices = [Index("id", unique = true)]
)
data class AccountRoomEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Long?,
    override val name: String,
    override val comment: String?,
    override val packageName: String
) : AccountEntity