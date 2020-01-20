[autoque](../../index.md) / [com.eakurnikov.autoque.data.repository](../index.md) / [CredentialsRepo](./index.md)

# CredentialsRepo

`interface CredentialsRepo`

Created by eakurnikov on 2019-10-05

### Properties

| Name | Summary |
|---|---|
| [credentialsSubject](credentials-subject.md) | `abstract val credentialsSubject: BehaviorSubject<Resource<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Credentials`](../../com.eakurnikov.autoque.data.model/-credentials/index.md)`>>>` |

### Functions

| Name | Summary |
|---|---|
| [getCredentials](get-credentials.md) | `abstract fun getCredentials(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [loadCredentials](load-credentials.md) | `abstract fun loadCredentials(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Inheritors

| Name | Summary |
|---|---|
| [CredentialsRepoImpl](../../com.eakurnikov.autoque.domain.repository/-credentials-repo-impl/index.md) | `class CredentialsRepoImpl : `[`CredentialsRepo`](./index.md)<br>Created by eakurnikov on 2019-10-05 |
