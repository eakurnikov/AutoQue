package com.eakurnikov.autoque.autofill.impl.di.components

import com.eakurnikov.autoque.autofill.api.api.AutofillFeatureApi
import com.eakurnikov.autoque.autofill.impl.di.dependencies.AutofillFeatureDependencies
import com.eakurnikov.autoque.autofill.impl.di.modules.AutofillFeatureModule
import com.eakurnikov.common.di.annotations.AppScope
import com.eakurnikov.common.di.initializer.ComponentInitializer
import dagger.Component

/**
 * Created by eakurnikov on 2019-09-15
 */
@AppScope
@Component(
    modules = [AutofillFeatureModule::class],
    dependencies = [AutofillFeatureDependencies::class]
)
abstract class AutofillFeatureComponent : AutofillFeatureApi {

    companion object :
        ComponentInitializer<AutofillFeatureDependencies, AutofillFeatureComponent>() {
        override fun buildComponent(param: AutofillFeatureDependencies): AutofillFeatureComponent {
            return DaggerAutofillFeatureComponent
                .builder()
                .autofillFeatureModule(AutofillFeatureModule())
                .autofillFeatureDependencies(param)
                .build()
        }
    }

    abstract fun createAutofillServiceComponent(): AutofillServiceComponent
}