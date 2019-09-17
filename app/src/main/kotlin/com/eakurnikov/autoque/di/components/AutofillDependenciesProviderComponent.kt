package com.eakurnikov.autoque.di.components

import android.content.Context
import com.eakurnikov.autoque.autofill.api.dependencies.AutofillDependenciesProvider
import com.eakurnikov.autoque.di.modules.autofill.AutofillAuthProviderModule
import com.eakurnikov.autoque.di.modules.autofill.AutofillDaoModule
import com.eakurnikov.autoque.di.modules.autofill.ContextModule
import com.eakurnikov.autoque.di.modules.autofill.DigitalAssetLinksVerifierModule
import com.eakurnikov.autoque.di.modules.autofill.PackageVerifierModule
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
        AutofillDaoModule::class,
        PackageVerifierModule::class,
        DigitalAssetLinksVerifierModule::class,
        AutofillAuthProviderModule::class
    ]
)
abstract class AutofillDependenciesProviderComponent : AutofillDependenciesProvider {

    companion object : ComponentInitializer<Context, AutofillDependenciesProviderComponent>() {
        override fun buildComponent(@AppContext param: Context): AutofillDependenciesProviderComponent {
            return DaggerAutofillDependenciesProviderComponent
                .builder()
                .contextModule(ContextModule(param))
                .autofillDaoModule(AutofillDaoModule())
                .packageVerifierModule(PackageVerifierModule())
                .digitalAssetLinksVerifierModule(DigitalAssetLinksVerifierModule())
                .autofillAuthProviderModule(AutofillAuthProviderModule())
                .build()
        }
    }
}