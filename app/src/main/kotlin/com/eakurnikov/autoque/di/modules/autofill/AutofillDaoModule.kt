package com.eakurnikov.autoque.di.modules.autofill

import com.eakurnikov.autoque.autofill.api.dependencies.data.dao.AutofillDao
import com.eakurnikov.autoque.data.AutofillDatabase
import com.eakurnikov.autoque.data.dao.AutofillDaoAdapter
import com.eakurnikov.common.di.annotations.AppScope
import dagger.Module
import dagger.Provides

/**
 * Created by eakurnikov on 2019-09-15
 */
@Module(includes = [AutofillDatabaseModule::class])
class AutofillDaoModule {

    @Provides
    @AppScope
    fun provideAutofillDao(db: AutofillDatabase): AutofillDao = AutofillDaoAdapter(db.autofillDao())
}