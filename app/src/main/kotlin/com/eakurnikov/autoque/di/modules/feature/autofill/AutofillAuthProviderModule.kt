package com.eakurnikov.autoque.di.modules.feature.autofill

import com.eakurnikov.autoque.autofill.api.dependencies.domain.auth.AutofillAuthProvider
import com.eakurnikov.autoque.domain.autofill.auth.AutofillAuthProviderImpl
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