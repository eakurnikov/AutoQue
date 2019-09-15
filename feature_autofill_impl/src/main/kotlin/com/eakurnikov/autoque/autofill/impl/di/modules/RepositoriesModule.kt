package com.eakurnikov.autoque.autofill.impl.di.modules

import com.eakurnikov.autoque.autofill.impl.data.repositories.AutofillRepository
import com.eakurnikov.autoque.autofill.impl.data.repositories.AutofillRepositoryImpl
import dagger.Binds
import dagger.Module

/**
 * Created by eakurnikov on 2019-09-15
 */
@Module
interface RepositoriesModule {

    @Binds
    fun bindAutofillRepository(impl: AutofillRepositoryImpl): AutofillRepository
}