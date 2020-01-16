package com.eakurnikov.autoque.di.modules.feature.autofill

import com.eakurnikov.autoque.autofill.api.dependencies.domain.disclaimer.AutofillDisclaimerProvider
import com.eakurnikov.autoque.domain.autofill.disclaimer.AutofillDisclaimerProviderImpl
import com.eakurnikov.common.di.annotations.AppScope
import dagger.Binds
import dagger.Module

/**
 * Created by eakurnikov on 2020-15-09
 */
@Module(includes = [AutofillDisclaimerProviderModule.Declarations::class])
class AutofillDisclaimerProviderModule {

    @Module
    interface Declarations {

        @Binds
        @AppScope
        fun bindAutofillDisclaimerProvider(
            impl: AutofillDisclaimerProviderImpl
        ): AutofillDisclaimerProvider<*>
    }
}