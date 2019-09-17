[autoque](../../index.md) / [com.eakurnikov.autoque.data.dao](../index.md) / [AutofillRoomDao](./index.md)

# AutofillRoomDao

`interface AutofillRoomDao`

Created by eakurnikov on 2019-09-15

### Functions

| Name | Summary |
|---|---|
| [addLoginToAccount](add-login-to-account.md) | `abstract fun addLoginToAccount(loginEntity: `[`LoginRoomEntity`](../../com.eakurnikov.autoque.data.entity/-login-room-entity/index.md)`): `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [createAccount](create-account.md) | `abstract fun createAccount(accountEntity: `[`AccountRoomEntity`](../../com.eakurnikov.autoque.data.entity/-account-room-entity/index.md)`): `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [getAccounts](get-accounts.md) | `abstract fun getAccounts(): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`AccountRoomEntity`](../../com.eakurnikov.autoque.data.entity/-account-room-entity/index.md)`>>` |
| [getLoginsForAccount](get-logins-for-account.md) | `abstract fun getLoginsForAccount(accountEntityId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`LoginRoomEntity`](../../com.eakurnikov.autoque.data.entity/-login-room-entity/index.md)`>>` |
