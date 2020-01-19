package com.eakurnikov.autoque.di.modules.common

import android.content.Context
import androidx.room.Room
import com.eakurnikov.autoque.data.db.AutoQueDatabase
import com.eakurnikov.autoque.data.db.AutoQueDatabaseNames.DATABASE_NAME
import com.eakurnikov.common.annotations.AppContext
import com.eakurnikov.common.di.annotations.AppScope
import dagger.Module
import dagger.Provides

/**
 * Created by eakurnikov on 2019-09-15
 */
@Module
class DatabaseModule {

    @Provides
    @AppScope
    fun provideAutoQueDatabase(@AppContext context: Context): AutoQueDatabase {
        return Room
            .databaseBuilder(context, AutoQueDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}