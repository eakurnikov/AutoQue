package com.eakurnikov.autoque.autofill.impl.di.modules

import com.eakurnikov.autoque.autofill.api.api.auth.AutofillAuthListener
import com.eakurnikov.autoque.autofill.api.api.registrar.AutofillServiceRegistrar
import com.eakurnikov.autoque.autofill.api.api.selector.AutofillServiceSelector
import com.eakurnikov.autoque.autofill.impl.api.auth.AutofillAuthListenerImpl
import com.eakurnikov.autoque.autofill.impl.api.registrar.AutofillServiceRegistrarImpl
import com.eakurnikov.autoque.autofill.impl.api.selector.AutofillServiceSelectorImpl
import dagger.Binds
import dagger.Module

/**
 * Created by eakurnikov on 2019-09-15
 */
@Module(
    includes = [
        RepositoriesModule::class,
        AutofillFeatureModule.Declarations::class
    ]
)
class AutofillFeatureModule {

    @Module
    interface Declarations {

        @Binds
        fun bindAutofillServiceRegistrar(impl: AutofillServiceRegistrarImpl): AutofillServiceRegistrar

        @Binds
        fun bindAutofillServiceSelector(impl: AutofillServiceSelectorImpl): AutofillServiceSelector

        @Binds
        fun bindAutofillAuthListener(impl: AutofillAuthListenerImpl): AutofillAuthListener
    }
}