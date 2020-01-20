[autoque](../../index.md) / [com.eakurnikov.autoque.data.network.api](../index.md) / [CredentialsApi](./index.md)

# CredentialsApi

`interface CredentialsApi`

Created by eakurnikov on 2019-12-15

### Functions

| Name | Summary |
|---|---|
| [getAccounts](get-accounts.md) | `abstract fun getAccounts(): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`AccountDto`](../../com.eakurnikov.autoque.data.network.dto/-account-dto/index.md)`>>` |
| [getLogins](get-logins.md) | `abstract fun getLogins(): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`LoginDto`](../../com.eakurnikov.autoque.data.network.dto/-login-dto/index.md)`>>` |
| [getLoginsForAccount](get-logins-for-account.md) | `abstract fun getLoginsForAccount(accountId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`LoginDto`](../../com.eakurnikov.autoque.data.network.dto/-login-dto/index.md)`>>?` |
