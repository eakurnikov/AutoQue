package com.eakurnikov.autoque.data.model

import com.eakurnikov.autoque.autofill.impl.internal.data.model.AccountDto
import com.eakurnikov.autoque.autofill.impl.internal.data.model.LoginDto
import com.eakurnikov.autoque.data.db.entity.AccountEntity
import com.eakurnikov.autoque.data.db.entity.LoginEntity

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

    constructor(
        accountDto: AccountDto,
        loginDto: LoginDto
    ) : this(
        0,
        0,
        accountDto.name,
        loginDto.login,
        loginDto.password,
        accountDto.packageName
    )

    constructor(
        name: String,
        url: String,
        login: String,
        password: String
    ): this(
        AccountDto(name, url),
        LoginDto(login, password)

    )
}