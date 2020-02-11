package com.eakurnikov.autoque.di.modules.feature.autofill

import com.eakurnikov.autoque.autofill.api.dependencies.domain.verification.AutofillClientVerifier
import com.eakurnikov.autoque.domain.autofill.verification.AutofillClientVerifierImpl
import com.eakurnikov.common.di.annotations.AppScope
import dagger.Binds
import dagger.Module

/**
 * Created by eakurnikov on 2019-09-15
 */
@Module(includes = [AutofillClientVerifierModule.Declarations::class])
class AutofillClientVerifierModule {

    @Module
    interface Declarations {

        @Binds
        @AppScope
        fun bindAutofillClientVerifier(impl: AutofillClientVerifierImpl): AutofillClientVerifier
    }
}