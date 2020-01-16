package com.eakurnikov.autoque.di.modules.feature.autofill

import com.eakurnikov.autoque.autofill.api.dependencies.domain.dal.DigitalAssetLinksVerifier
import com.eakurnikov.autoque.domain.autofill.dal.DigitalAssetLinksVerifierImpl
import com.eakurnikov.common.di.annotations.AppScope
import dagger.Binds
import dagger.Module

/**
 * Created by eakurnikov on 2019-09-15
 */
@Module(includes = [DigitalAssetLinksVerifierModule.Declarations::class])
class DigitalAssetLinksVerifierModule {

    @Module
    interface Declarations {

        @Binds
        @AppScope
        fun bindDigitalAssetLinksVerifier(
            impl: DigitalAssetLinksVerifierImpl
        ): DigitalAssetLinksVerifier
    }
}