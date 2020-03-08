package com.eakurnikov.autoque.autofill.impl.di.modules

import com.eakurnikov.autoque.autofill.api.api.domain.enable.AutofillServiceEnabler
import com.eakurnikov.autoque.autofill.api.api.domain.select.AutofillServiceSelector
import com.eakurnikov.autoque.autofill.api.api.presentation.auth.AutofillAuthPresenter
import com.eakurnikov.autoque.autofill.impl.api.domain.enable.AutofillServiceEnablerImpl
import com.eakurnikov.autoque.autofill.impl.api.domain.select.AutofillServiceSelectorImpl
import com.eakurnikov.autoque.autofill.impl.api.presentation.auth.AutofillAuthPresenterImpl
import com.eakurnikov.autoque.autofill.impl.internal.data.repositories.AutofillRepository
import com.eakurnikov.autoque.autofill.impl.internal.data.repositories.AutofillRepositoryImpl
import com.eakurnikov.autoque.autofill.impl.internal.domain.providers.disclaimer.DisclaimerProvider
import com.eakurnikov.autoque.autofill.impl.internal.domain.providers.disclaimer.DisclaimerProviderImpl
import com.eakurnikov.autoque.autofill.impl.internal.domain.providers.update.UpdatePromptProvider
import com.eakurnikov.autoque.autofill.impl.internal.domain.providers.update.UpdatePromptProviderImpl
import com.eakurnikov.autoque.autofill.impl.internal.domain.verification.AutofillClientVerifier
import com.eakurnikov.autoque.autofill.impl.internal.domain.verification.AutofillClientVerifierImpl
import dagger.Binds
import dagger.Module

/**
 * Created by eakurnikov on 2019-09-15
 */
@Module(
    includes = [
        ViewModelModule::class,
        ActivityBuilderModule::class,
        AutofillFeatureModule.Declarations::class
    ]
)
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
        fun bindAutofillClientVerifier(impl: AutofillClientVerifierImpl): AutofillClientVerifier

        @Binds
        fun bindUpdatePromptProvider(impl: UpdatePromptProviderImpl): UpdatePromptProvider

        @Binds
        fun bindDisclaimerProvider(impl: DisclaimerProviderImpl): DisclaimerProvider
    }
}