package com.eakurnikov.autoque.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.eakurnikov.autoque.autofill.api.dependencies.data.entity.LoginEntity
import com.eakurnikov.autoque.data.AutofillDatabaseNames.LOGINS_TABLE_NAME

/**
 * Created by eakurnikov on 2019-09-15
 */
@Entity(
    tableName = LOGINS_TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = AccountRoomEntity::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("id", unique = true)]
)
data class LoginRoomEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Long?,
    val accountId: Long,
    override val name: String,
    override val login: String,
    override val password: String,
    override val comment: String?
) : LoginEntity