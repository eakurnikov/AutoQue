[autoque](../../index.md) / [com.eakurnikov.autoque.data.db.dao](../index.md) / [AutofillDaoAdapter](./index.md)

# AutofillDaoAdapter

`class AutofillDaoAdapter : AutofillDao`

Created by eakurnikov on 2019-09-15

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `AutofillDaoAdapter(dao: `[`CredentialsDao`](../-credentials-dao/index.md)`)`<br>Created by eakurnikov on 2019-09-15 |

### Functions

| Name | Summary |
|---|---|
| [addAccountWithLogin](add-account-with-login.md) | `fun addAccountWithLogin(account: Account, login: Login): Completable` |
| [addLoginToAccount](add-login-to-account.md) | `fun addLoginToAccount(account: Account, login: Login): Completable` |
| [getAccountById](get-account-by-id.md) | `fun getAccountById(accountId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): Single<Account>` |
| [getAccounts](get-accounts.md) | `fun getAccounts(): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<Account>>` |
| [getLoginById](get-login-by-id.md) | `fun getLoginById(loginId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): Single<Login>` |
| [getLoginsForAccount](get-logins-for-account.md) | `fun getLoginsForAccount(accountId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): Single<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<Login>>` |
| [updateLoginInAccount](update-login-in-account.md) | `fun updateLoginInAccount(account: Account, login: Login): Completable` |
