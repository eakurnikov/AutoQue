[autoque](../../index.md) / [com.eakurnikov.autoque.domain.repository](../index.md) / [CredentialsRepoImpl](./index.md)

# CredentialsRepoImpl

`class CredentialsRepoImpl : `[`CredentialsRepo`](../../com.eakurnikov.autoque.data.repository/-credentials-repo/index.md)

Created by eakurnikov on 2019-10-05

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CredentialsRepoImpl(api: `[`CredentialsApi`](../../com.eakurnikov.autoque.data.network.api/-credentials-api/index.md)`, dao: `[`CredentialsDao`](../../com.eakurnikov.autoque.data.db.dao/-credentials-dao/index.md)`)`<br>Created by eakurnikov on 2019-10-05 |

### Properties

| Name | Summary |
|---|---|
| [credentialsSubject](credentials-subject.md) | `val credentialsSubject: BehaviorSubject<Resource<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Credentials`](../../com.eakurnikov.autoque.data.model/-credentials/index.md)`>>>` |

### Functions

| Name | Summary |
|---|---|
| [getCredentials](get-credentials.md) | `fun getCredentials(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [loadCredentials](load-credentials.md) | `fun loadCredentials(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
