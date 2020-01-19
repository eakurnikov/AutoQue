package com.eakurnikov.autoque.data.model

import com.eakurnikov.autoque.data.db.entity.AccountEntity
import com.eakurnikov.autoque.data.db.entity.LoginEntity
import com.eakurnikov.autoque.data.network.dto.AccountDto
import com.eakurnikov.autoque.data.network.dto.LoginDto

/**
 * Created by eakurnikov on 2019-12-15
 */
data class Credentials(
    val accountId: Long,
    val loginId: Long,
    val accountName: String,
    val login: String,
    val password: String,
    val packageName: String
) {
    constructor(
        accountDto: AccountDto,
        loginDto: LoginDto
    ) : this(
        accountDto.id,
        loginDto.id,
        accountDto.name,
        loginDto.login,
        loginDto.password,
        accountDto.packageName
    )

    constructor(
        accountEntity: AccountEntity,
        loginEntity: LoginEntity
    ) : this(
        accountEntity.id!!,
        loginEntity.id!!,
        accountEntity.name,
        loginEntity.login,
        loginEntity.password,
        accountEntity.packageName
    )
}