[autoque](../../index.md) / [com.eakurnikov.autoque.data.dao](../index.md) / [AutofillDaoAdapter](./index.md)

# AutofillDaoAdapter

`class AutofillDaoAdapter : AutofillDao`

Created by eakurnikov on 2019-09-15

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `AutofillDaoAdapter(dao: `[`AutofillRoomDao`](../-autofill-room-dao/index.md)`)`<br>Created by eakurnikov on 2019-09-15 |

### Functions

| Name | Summary |
|---|---|
| [addAccountWithLogin](add-account-with-login.md) | `fun addAccountWithLogin(accountEntity: AccountEntity, loginEntity: LoginEntity): Completable` |
| [getAccounts](get-accounts.md) | `fun getAccounts(): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<AccountEntity>>` |
| [getLoginsForAccount](get-logins-for-account.md) | `fun getLoginsForAccount(accountId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<LoginEntity>>` |
