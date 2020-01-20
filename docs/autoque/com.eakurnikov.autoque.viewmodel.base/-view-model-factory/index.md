[autoque](../../index.md) / [com.eakurnikov.autoque.viewmodel.base](../index.md) / [ViewModelFactory](./index.md)

# ViewModelFactory

`@AppScope class ViewModelFactory : Factory`

Created by eakurnikov on 2019-10-07

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ViewModelFactory(creators: `[`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)`<`[`Class`](https://developer.android.com/reference/java/lang/Class.html)`<out ViewModel>, Provider<ViewModel>>)`<br>Created by eakurnikov on 2019-10-07 |

### Functions

| Name | Summary |
|---|---|
| [create](create.md) | `fun <T : ViewModel> create(modelClass: `[`Class`](https://developer.android.com/reference/java/lang/Class.html)`<`[`T`](create.md#T)`>): `[`T`](create.md#T) |
