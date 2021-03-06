package com.eakurnikov.autoque.di.components

import android.content.Context
import com.eakurnikov.autoque.autofill.api.dependencies.AutofillDependenciesProvider
import com.eakurnikov.autoque.di.modules.common.ContextModule
import com.eakurnikov.autoque.di.modules.common.DaoModule
import com.eakurnikov.autoque.di.modules.feature.autofill.*
import com.eakurnikov.common.annotations.AppContext
import com.eakurnikov.common.di.annotations.AppScope
import com.eakurnikov.common.di.initializer.ComponentInitializer
import dagger.Component

/**
 * Created by eakurnikov on 2019-09-15
 */
@AppScope
@Component(
    modules = [
        ContextModule::class,
        DaoModule::class,
        AutofillAuthProviderModule::class
    ]
)
abstract class AutofillDependenciesProviderComponent : AutofillDependenciesProvider {

    companion object : ComponentInitializer<Context, AutofillDependenciesProviderComponent>() {
        override fun buildComponent(@AppContext param: Context): AutofillDependenciesProviderComponent {
            return DaggerAutofillDependenciesProviderComponent
                .builder()
                .contextModule(ContextModule(param))
                .daoModule(DaoModule())
                .autofillAuthProviderModule(AutofillAuthProviderModule())
                .build()
        }
    }
}