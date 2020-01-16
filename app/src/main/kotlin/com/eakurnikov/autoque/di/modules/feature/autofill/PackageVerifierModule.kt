package com.eakurnikov.autoque.di.modules.feature.autofill

import com.eakurnikov.autoque.autofill.api.dependencies.domain.packagename.PackageVerifier
import com.eakurnikov.autoque.domain.autofill.packagename.PackageVerifierImpl
import com.eakurnikov.common.di.annotations.AppScope
import dagger.Binds
import dagger.Module

/**
 * Created by eakurnikov on 2019-09-15
 */
@Module(includes = [PackageVerifierModule.Declarations::class])
class PackageVerifierModule {

    @Module
    interface Declarations {

        @Binds
        @AppScope
        fun bindPackageVerifier(impl: PackageVerifierImpl): PackageVerifier
    }
}