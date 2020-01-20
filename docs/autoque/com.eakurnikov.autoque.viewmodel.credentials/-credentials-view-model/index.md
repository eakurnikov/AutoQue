[autoque](../../index.md) / [com.eakurnikov.autoque.viewmodel.credentials](../index.md) / [CredentialsViewModel](./index.md)

# CredentialsViewModel

`class CredentialsViewModel : `[`BaseViewModel`](../../com.eakurnikov.autoque.viewmodel.base/-base-view-model/index.md)

Created by eakurnikov on 2019-12-15

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CredentialsViewModel(repo: `[`CredentialsRepo`](../../com.eakurnikov.autoque.data.repository/-credentials-repo/index.md)`)`<br>Created by eakurnikov on 2019-12-15 |

### Properties

| Name | Summary |
|---|---|
| [credentialsSubject](credentials-subject.md) | `var credentialsSubject: BehaviorSubject<Resource<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Credentials`](../../com.eakurnikov.autoque.data.model/-credentials/index.md)`>>>` |

### Functions

| Name | Summary |
|---|---|
| [onRefresh](on-refresh.md) | `fun onRefresh(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onStart](on-start.md) | `fun onStart(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [subscribe](subscribe.md) | `fun subscribe(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Inherited Functions

| Name | Summary |
|---|---|
| [dispose](../../com.eakurnikov.autoque.viewmodel.base/-base-view-model/dispose.md) | `open fun dispose(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onStop](../../com.eakurnikov.autoque.viewmodel.base/-base-view-model/on-stop.md) | `open fun onStop(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [subscribe](../../com.eakurnikov.autoque.viewmodel.base/-base-view-model/subscribe.md) | `open fun subscribe(disposable: Disposable): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
