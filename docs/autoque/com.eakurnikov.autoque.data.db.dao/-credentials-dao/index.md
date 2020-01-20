[autoque](../../index.md) / [com.eakurnikov.autoque.data.db.dao](../index.md) / [CredentialsDao](./index.md)

# CredentialsDao

`interface CredentialsDao`

Created by eakurnikov on 2019-09-15

### Functions

| Name | Summary |
|---|---|
| [addLoginsToAccount](add-logins-to-account.md) | `abstract fun addLoginsToAccount(loginEntities: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`LoginEntity`](../../com.eakurnikov.autoque.data.db.entity/-login-entity/index.md)`>): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`>` |
| [addLoginToAccount](add-login-to-account.md) | `abstract fun addLoginToAccount(loginEntity: `[`LoginEntity`](../../com.eakurnikov.autoque.data.db.entity/-login-entity/index.md)`): `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [createAccount](create-account.md) | `abstract fun createAccount(accountEntity: `[`AccountEntity`](../../com.eakurnikov.autoque.data.db.entity/-account-entity/index.md)`): `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [createAccountsList](create-accounts-list.md) | `abstract fun createAccountsList(accountEntities: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`AccountEntity`](../../com.eakurnikov.autoque.data.db.entity/-account-entity/index.md)`>): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`>` |
| [getAccountById](get-account-by-id.md) | `abstract fun getAccountById(accountId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): Single<`[`AccountEntity`](../../com.eakurnikov.autoque.data.db.entity/-account-entity/index.md)`>` |
| [getAccounts](get-accounts.md) | `abstract fun getAccounts(): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`AccountEntity`](../../com.eakurnikov.autoque.data.db.entity/-account-entity/index.md)`>>` |
| [getLoginById](get-login-by-id.md) | `abstract fun getLoginById(loginId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): Single<`[`LoginEntity`](../../com.eakurnikov.autoque.data.db.entity/-login-entity/index.md)`>` |
| [getLoginsForAccount](get-logins-for-account.md) | `abstract fun getLoginsForAccount(accountId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`LoginEntity`](../../com.eakurnikov.autoque.data.db.entity/-login-entity/index.md)`>>` |
| [updateLogin](update-login.md) | `abstract fun updateLogin(loginEntity: `[`LoginEntity`](../../com.eakurnikov.autoque.data.db.entity/-login-entity/index.md)`): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
