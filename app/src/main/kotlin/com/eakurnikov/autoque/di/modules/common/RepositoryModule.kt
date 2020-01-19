package com.eakurnikov.autoque.di.modules.common

import com.eakurnikov.autoque.data.repository.CredentialsRepo
import com.eakurnikov.autoque.domain.repository.CredentialsRepoImpl
import com.eakurnikov.common.di.annotations.AppScope
import dagger.Binds
import dagger.Module

/**
 * Created by eakurnikov on 2019-10-07
 */
@Module(
    includes = [
        ApiModule::class,
        DaoModule::class
    ]
)
interface RepositoryModule {

    @Binds
    @AppScope
    fun bindCredentialsRepo(impl: CredentialsRepoImpl): CredentialsRepo
}