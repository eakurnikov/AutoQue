package com.eakurnikov.autoque

import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataDto
import com.eakurnikov.autoque.data.db.entity.AccountEntity
import com.eakurnikov.autoque.data.db.entity.LoginEntity

fun createFillData(
    id: Long,
    accountName: String,
    packageName: String
) = FillDataDto(
    AccountEntityImpl(id, accountName, packageName),
    LoginEntityImpl(id, "", "example@gmail.com", "12345")
)

class AccountEntityImpl(
    private val id: Long,
    private val name: String,
    private val packageName: String
) : AccountEntity {
    override fun getId(): Long = id
    override fun getName(): String = name
    override fun getComment(): String? = null
    override fun getStringIdentifier(): String = getPackageName()
    override fun getPackageName(): String = packageName
    override fun toString(): String = "[ID:$id NAME:$name PN:$packageName]"
}

class LoginEntityImpl(
    private val id: Long,
    private val name: String,
    private val login: String,
    private val password: String
) : LoginEntity {
    override fun getId(): Long = id
    override fun getName(): String = name
    override fun getComment(): String? = null
    override fun getLogin(): String = login
    override fun getPassword(): String = password
    override fun toString(): String = "[ID:$id LGN:$login PW:$password]"
}