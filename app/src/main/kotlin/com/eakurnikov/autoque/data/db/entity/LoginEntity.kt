package com.eakurnikov.autoque.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.Login
import com.eakurnikov.autoque.data.db.AutoQueDatabaseNames.LOGINS_TABLE_NAME
import com.eakurnikov.autoque.data.network.dto.LoginDto

/**
 * Created by eakurnikov on 2019-09-15
 */
@Entity(
    tableName = LOGINS_TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("id", unique = true)]
)
data class LoginEntity(
    @PrimaryKey(autoGenerate = true)
    override var id: Long?,
    val accountId: Long,
    override val login: String,
    override val password: String,
    override val comment: String?
) : Login {
    constructor(
        loginDto: LoginDto
    ) : this(
        loginDto.id,
        loginDto.accountId,
        loginDto.login,
        loginDto.password,
        loginDto.comment
    )
}