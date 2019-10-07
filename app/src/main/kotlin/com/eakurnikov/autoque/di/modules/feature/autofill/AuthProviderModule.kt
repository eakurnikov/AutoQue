package com.eakurnikov.autoque.di.modules.feature.autofill

import com.eakurnikov.autoque.autofill.api.dependencies.auth.AuthProvider
import com.eakurnikov.autoque.dependencies.auth.AuthProviderImpl
import com.eakurnikov.common.di.annotations.AppScope
import dagger.Binds
import dagger.Module

/**
 * Created by eakurnikov on 2019-09-15
 */
@Module(includes = [AuthProviderModule.Declarations::class])
class AuthProviderModule {

    @Module
    interface Declarations {

        @Binds
        @AppScope
        fun bindAuthProvider(impl: AuthProviderImpl): AuthProvider<*>
    }
}