package com.eakurnikov.autoque.di.modules.feature.autofill

import com.eakurnikov.autoque.autofill.api.dependencies.domain.update.AutofillUpdatePromptProvider
import com.eakurnikov.autoque.domain.autofill.update.AutofillUpdatePromptProviderImpl
import com.eakurnikov.common.di.annotations.AppScope
import dagger.Binds
import dagger.Module

/**
 * Created by eakurnikov on 2020-01-09
 */
@Module(includes = [AutofillUpdatePromptProviderModule.Declarations::class])
class AutofillUpdatePromptProviderModule {

    @Module
    interface Declarations {

        @Binds
        @AppScope
        fun bindAutofillUpdatePromptProvider(
            impl: AutofillUpdatePromptProviderImpl
        ): AutofillUpdatePromptProvider<*>
    }
}