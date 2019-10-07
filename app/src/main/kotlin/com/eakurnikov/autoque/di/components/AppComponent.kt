package com.eakurnikov.autoque.di.components

import com.eakurnikov.autoque.di.modules.common.*
import com.eakurnikov.autoque.di.modules.feature.autofill.AutofillFeatureApiModule
import com.eakurnikov.autoque.domain.app.AutoQueApp
import com.eakurnikov.common.di.annotations.AppScope
import dagger.Component
import dagger.android.AndroidInjector

/**
 * Created by eakurnikov on 2019-09-15
 */
@AppScope
@Component(
    modules = [
        AppModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        ActivityBuilderModule::class,
        AutofillFeatureApiModule::class
    ]
)
interface AppComponent : AndroidInjector<AutoQueApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<AutoQueApp>()
}