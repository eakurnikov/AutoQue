package com.eakurnikov.autoque.autofill.impl.data.model

import com.eakurnikov.autoque.autofill.api.dependencies.data.entity.AccountEntity
import com.eakurnikov.autoque.autofill.api.dependencies.data.entity.LoginEntity

//data class LoginWithAccountIdDto(
//        val accountEntityId: EntityId,
//        val loginDto: LoginDto
//)

/**
 * Created by eakurnikov on 2019-09-15
 */

data class FillDataEntity(
    val accountEntity: AccountEntity,
    val loginEntity: LoginEntity
)

data class AccountEntityImpl(
    override val id: Long?,
    override val name: String,
    override val comment: String?,
    override val packageName: String
) : AccountEntity

data class LoginEntityImpl(
    override val id: Long?,
    override val name: String,
    override val login: String,
    override val password: String,
    override val comment: String?
) : LoginEntity