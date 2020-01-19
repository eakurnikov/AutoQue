package com.eakurnikov.autoque.di.modules.common

import com.eakurnikov.autoque.autofill.api.dependencies.data.dao.AutofillDao
import com.eakurnikov.autoque.data.db.AutoQueDatabase
import com.eakurnikov.autoque.data.db.dao.AutofillDaoAdapter
import com.eakurnikov.autoque.data.db.dao.CredentialsDao
import com.eakurnikov.common.di.annotations.AppScope
import dagger.Module
import dagger.Provides

/**
 * Created by eakurnikov on 2019-10-07
 */
@Module(includes = [DatabaseModule::class])
class DaoModule {

    @Provides
    @AppScope
    fun provideCredentialsDao(db: AutoQueDatabase): CredentialsDao =
        db.credentialsDao()

    @Provides
    @AppScope
    fun provideAutofillDao(credentialsDao: CredentialsDao): AutofillDao =
        AutofillDaoAdapter(credentialsDao)
}