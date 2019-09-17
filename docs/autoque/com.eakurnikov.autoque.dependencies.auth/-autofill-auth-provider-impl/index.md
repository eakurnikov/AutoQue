[autoque](../../index.md) / [com.eakurnikov.autoque.dependencies.auth](../index.md) / [AutofillAuthProviderImpl](./index.md)

# AutofillAuthProviderImpl

`@AppScope class AutofillAuthProviderImpl : AutofillAuthProvider<`[`AuthActivity`](../../com.eakurnikov.autoque.view/-auth-activity/index.md)`>`

Created by eakurnikov on 2019-09-15

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `AutofillAuthProviderImpl(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`)`<br>Created by eakurnikov on 2019-09-15 |

### Properties

| Name | Summary |
|---|---|
| [autofillAuthenticatorClass](autofill-authenticator-class.md) | `val autofillAuthenticatorClass: `[`Class`](https://developer.android.com/reference/java/lang/Class.html)`<`[`AuthActivity`](../../com.eakurnikov.autoque.view/-auth-activity/index.md)`>` |
| [isAuthRequired](is-auth-required.md) | `val isAuthRequired: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

### Functions

| Name | Summary |
|---|---|
| [getAuthIntentSenderForFill](get-auth-intent-sender-for-fill.md) | `fun getAuthIntentSenderForFill(clientState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`): `[`IntentSender`](https://developer.android.com/reference/android/content/IntentSender.html) |
| [getAuthIntentSenderForSave](get-auth-intent-sender-for-save.md) | `fun getAuthIntentSenderForSave(clientState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`): `[`IntentSender`](https://developer.android.com/reference/android/content/IntentSender.html) |
