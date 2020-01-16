package com.eakurnikov.autoque.autofill.impl.internal.data.model

import com.eakurnikov.autoque.autofill.api.dependencies.data.model.Account
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.Login

/**
 * Created by eakurnikov on 2019-09-15
 */
data class FillDataDto(
    val account: Account,
    val login: Login
) {
    val id: FillDataId
        get() = FillDataId(account.id, login.id)
}

data class FillDataId(
    val accountId: Long?,
    val loginId: Long?
)

data class AccountDto(
    override val id: Long?,
    override val name: String,
    override val comment: String?,
    override val packageName: String
) : Account {
    constructor(
        name: String,
        packageName: String
    ) : this(
        null,
        name,
        null,
        packageName
    )
}

data class LoginDto(
    override val id: Long?,
    override val login: String,
    override val password: String,
    override val comment: String?
) : Login {
    constructor(
        login: String,
        password: String
    ) : this(
        null,
        login,
        password,
        null
    )
}