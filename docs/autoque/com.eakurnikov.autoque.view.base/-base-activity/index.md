[autoque](../../index.md) / [com.eakurnikov.autoque.view.base](../index.md) / [BaseActivity](./index.md)

# BaseActivity

`abstract class BaseActivity<ViewModel : `[`BaseViewModel`](../../com.eakurnikov.autoque.viewmodel.base/-base-view-model/index.md)`> : DaggerAppCompatActivity`

Created by eakurnikov on 2019-10-07

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `BaseActivity()`<br>Created by eakurnikov on 2019-10-07 |

### Properties

| Name | Summary |
|---|---|
| [viewModel](view-model.md) | `abstract var viewModel: `[`ViewModel`](index.md#ViewModel) |
| [viewModelFactory](view-model-factory.md) | `lateinit var viewModelFactory: Factory` |

### Functions

| Name | Summary |
|---|---|
| [onStart](on-start.md) | `open fun onStart(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onStop](on-stop.md) | `open fun onStop(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Inheritors

| Name | Summary |
|---|---|
| [CredentialsActivity](../../com.eakurnikov.autoque.view.credentials/-credentials-activity/index.md) | `class CredentialsActivity : `[`BaseActivity`](./index.md)`<`[`CredentialsViewModel`](../../com.eakurnikov.autoque.viewmodel.credentials/-credentials-view-model/index.md)`>`<br>Created by eakurnikov on 2019-12-15 |
