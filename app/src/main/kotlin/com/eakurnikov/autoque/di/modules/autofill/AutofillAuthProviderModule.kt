package com.eakurnikov.autoque.di.modules.autofill

import com.eakurnikov.autoque.autofill.api.dependencies.auth.AutofillAuthProvider
import com.eakurnikov.autoque.dependencies.auth.AutofillAuthProviderImpl
import com.eakurnikov.common.di.annotations.AppScope
import dagger.Binds
import dagger.Module

/**
 * Created by eakurnikov on 2019-09-15
 */
@Module(includes = [AutofillAuthProviderModule.Declarations::class])
class AutofillAuthProviderModule {

    @Module
    interface Declarations {

        @Binds
        @AppScope
        fun bindAutofillAuthProvider(impl: AutofillAuthProviderImpl): AutofillAuthProvider<*>
    }
}