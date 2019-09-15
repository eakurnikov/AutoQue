package com.eakurnikov.autoque.di.modules

import android.content.Context
import com.eakurnikov.autoque.domain.app.AutoQueApp
import com.eakurnikov.common.annotations.AppContext
import com.eakurnikov.common.di.annotations.AppScope
import dagger.Binds
import dagger.Module

/**
 * Created by eakurnikov on 2019-09-15
 */
@Module
interface AppModule {

    @Binds
    @AppScope
    @AppContext
    fun bindContext(app: AutoQueApp): Context
}