[autoque](../../index.md) / [com.eakurnikov.autoque.view.credentials](../index.md) / [CredentialsActivity](./index.md)

# CredentialsActivity

`class CredentialsActivity : `[`BaseActivity`](../../com.eakurnikov.autoque.view.base/-base-activity/index.md)`<`[`CredentialsViewModel`](../../com.eakurnikov.autoque.viewmodel.credentials/-credentials-view-model/index.md)`>`

Created by eakurnikov on 2019-12-15

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CredentialsActivity()`<br>Created by eakurnikov on 2019-12-15 |

### Properties

| Name | Summary |
|---|---|
| [autofillApi](autofill-api.md) | `lateinit var autofillApi: AutofillFeatureApi` |
| [viewModel](view-model.md) | `lateinit var viewModel: `[`CredentialsViewModel`](../../com.eakurnikov.autoque.viewmodel.credentials/-credentials-view-model/index.md) |

### Inherited Properties

| Name | Summary |
|---|---|
| [viewModelFactory](../../com.eakurnikov.autoque.view.base/-base-activity/view-model-factory.md) | `lateinit var viewModelFactory: Factory` |

### Functions

| Name | Summary |
|---|---|
| [onActivityResult](on-activity-result.md) | `fun onActivityResult(requestCode: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, resultCode: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, data: `[`Intent`](https://developer.android.com/reference/android/content/Intent.html)`?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onCreate](on-create.md) | `fun onCreate(savedInstanceState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onStart](on-start.md) | `fun onStart(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onStop](on-stop.md) | `fun onStop(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Companion Object Functions

| Name | Summary |
|---|---|
| [start](start.md) | `fun start(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
