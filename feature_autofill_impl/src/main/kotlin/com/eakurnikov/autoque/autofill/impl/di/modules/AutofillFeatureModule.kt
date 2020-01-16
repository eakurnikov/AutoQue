package com.eakurnikov.autoque.autofill.impl.di.modules

import com.eakurnikov.autoque.autofill.api.api.domain.enable.AutofillServiceEnabler
import com.eakurnikov.autoque.autofill.api.api.domain.select.AutofillServiceSelector
import com.eakurnikov.autoque.autofill.api.api.presentation.auth.AutofillAuthPresenter
import com.eakurnikov.autoque.autofill.api.api.presentation.disclaimer.AutofillDisclaimerPresenter
import com.eakurnikov.autoque.autofill.api.api.presentation.update.AutofillUpdatePromptPresenter
import com.eakurnikov.autoque.autofill.impl.api.domain.enable.AutofillServiceEnablerImpl
import com.eakurnikov.autoque.autofill.impl.api.domain.select.AutofillServiceSelectorImpl
import com.eakurnikov.autoque.autofill.impl.api.presentation.auth.AutofillAuthPresenterImpl
import com.eakurnikov.autoque.autofill.impl.api.presentation.disclaimer.AutofillDisclaimerPresenterImpl
import com.eakurnikov.autoque.autofill.impl.api.presentation.update.AutofillUpdatePromptPresenterImpl
import com.eakurnikov.autoque.autofill.impl.internal.data.repositories.AutofillRepository
import com.eakurnikov.autoque.autofill.impl.internal.data.repositories.AutofillRepositoryImpl
import dagger.Binds
import dagger.Module

/**
 * Created by eakurnikov on 2019-09-15
 */
@Module(includes = [AutofillFeatureModule.Declarations::class])
class AutofillFeatureModule {

    @Module
    interface Declarations {

        @Binds
        fun bindAutofillRepository(impl: AutofillRepositoryImpl): AutofillRepository

        @Binds
        fun bindAutofillServiceEnabler(impl: AutofillServiceEnablerImpl): AutofillServiceEnabler

        @Binds
        fun bindAutofillServiceSelector(impl: AutofillServiceSelectorImpl): AutofillServiceSelector

        @Binds
        fun bindAutofillAuthPresenter(impl: AutofillAuthPresenterImpl): AutofillAuthPresenter

        @Binds
        fun bindAutofillDisclaimerPresenter(
            impl: AutofillDisclaimerPresenterImpl
        ): AutofillDisclaimerPresenter

        @Binds
        fun bindAutofillUpdatePromptPresenter(
            impl: AutofillUpdatePromptPresenterImpl
        ): AutofillUpdatePromptPresenter
    }
}