package com.eakurnikov.autoque.data.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.Account
import com.eakurnikov.autoque.data.db.AutoQueDatabaseNames.ACCOUNTS_TABLE_NAME
import com.eakurnikov.autoque.data.network.dto.CredentialsDto

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
) : Account {
    constructor(
        credentialsDto: CredentialsDto
    ) : this(
        null,
        credentialsDto.name,
        credentialsDto.comment,
        credentialsDto.packageName
    )
}