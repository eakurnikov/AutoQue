[autoque](../../index.md) / [com.eakurnikov.autoque.domain.autofill.auth](../index.md) / [AutofillAuthProviderImpl](./index.md)

# AutofillAuthProviderImpl

`@AppScope class AutofillAuthProviderImpl : AutofillAuthProvider<`[`AuthActivity`](../../com.eakurnikov.autoque.view.auth/-auth-activity/index.md)`>`

Created by eakurnikov on 2019-09-15

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `AutofillAuthProviderImpl(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`)`<br>Created by eakurnikov on 2019-09-15 |

### Properties

| Name | Summary |
|---|---|
| [autofillAuthUiClass](autofill-auth-ui-class.md) | `val autofillAuthUiClass: `[`Class`](https://developer.android.com/reference/java/lang/Class.html)`<`[`AuthActivity`](../../com.eakurnikov.autoque.view.auth/-auth-activity/index.md)`>` |
| [isAuthRequired](is-auth-required.md) | `val isAuthRequired: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

### Functions

| Name | Summary |
|---|---|
| [getFillAuthIntentSender](get-fill-auth-intent-sender.md) | `fun getFillAuthIntentSender(clientState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`): `[`IntentSender`](https://developer.android.com/reference/android/content/IntentSender.html) |
| [getSaveAuthIntentSender](get-save-auth-intent-sender.md) | `fun getSaveAuthIntentSender(clientState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`): `[`IntentSender`](https://developer.android.com/reference/android/content/IntentSender.html) |
| [getUnsafeFillAuthIntentSender](get-unsafe-fill-auth-intent-sender.md) | `fun getUnsafeFillAuthIntentSender(clientState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`): `[`IntentSender`](https://developer.android.com/reference/android/content/IntentSender.html) |
| [getUpdateAuthIntentSender](get-update-auth-intent-sender.md) | `fun getUpdateAuthIntentSender(clientState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`): `[`IntentSender`](https://developer.android.com/reference/android/content/IntentSender.html) |
