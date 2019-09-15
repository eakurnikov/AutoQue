package com.eakurnikov.autoque.di.modules.autofill

import com.eakurnikov.autoque.autofill.api.dependencies.dal.DigitalAssetLinksVerifier
import com.eakurnikov.autoque.dependencies.dal.DigitalAssetLinksVerifierImpl
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
        fun bindDigitalAssetLinksVerifier(impl: DigitalAssetLinksVerifierImpl): DigitalAssetLinksVerifier
    }
}