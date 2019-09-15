package com.eakurnikov.autoque.di.modules.autofill

import android.content.Context
import androidx.room.Room
import com.eakurnikov.autoque.data.AutofillDatabaseNames.DATABASE_NAME
import com.eakurnikov.autoque.data.AutofillDatabase
import com.eakurnikov.common.annotations.AppContext
import com.eakurnikov.common.di.annotations.AppScope
import dagger.Module
import dagger.Provides

/**
 * Created by eakurnikov on 2019-09-15
 */
@Module
class AutofillDatabaseModule {

    @Provides
    @AppScope
    fun provideAutofillDatabase(@AppContext context: Context): AutofillDatabase {
        return Room
            .databaseBuilder(context, AutofillDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}