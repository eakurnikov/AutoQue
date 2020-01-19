package com.eakurnikov.autoque.di.modules.common

import com.eakurnikov.autoque.data.network.api.CredentialsApi
import com.eakurnikov.common.di.annotations.AppScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by eakurnikov on 2019-10-07
 */
@Module(includes = [NetworkModule::class])
open class ApiModule {

    @Provides
    @AppScope
    open fun provideCredentialsApi(retrofit: Retrofit): CredentialsApi =
        retrofit.create(CredentialsApi::class.java)
}