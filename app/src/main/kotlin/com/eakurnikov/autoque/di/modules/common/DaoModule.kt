package com.eakurnikov.autoque.di.modules.common

import com.eakurnikov.autoque.autofill.api.dependencies.data.dao.AutofillDao
import com.eakurnikov.autoque.data.db.AutoQueDatabase
import com.eakurnikov.autoque.data.db.dao.autofill.AutofillDaoAdapter
import com.eakurnikov.autoque.data.db.dao.main.MainDao
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
    fun provideAutofillDao(db: AutoQueDatabase): AutofillDao = AutofillDaoAdapter(db.autofillDao())

    @Provides
    @AppScope
    fun provideMainDao(db: AutoQueDatabase): MainDao = db.mainDao()
}