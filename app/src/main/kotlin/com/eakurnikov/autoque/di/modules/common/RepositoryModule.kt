package com.eakurnikov.autoque.di.modules.common

import com.eakurnikov.autoque.data.repository.MainRepo
import com.eakurnikov.autoque.domain.repository.MainRepoImpl
import com.eakurnikov.common.di.annotations.AppScope
import dagger.Binds
import dagger.Module

/**
 * Created by eakurnikov on 2019-10-07
 */
@Module(includes = [DaoModule::class])
interface RepositoryModule {

    @Binds
    @AppScope
    fun bindMainRepo(impl: MainRepoImpl): MainRepo
}