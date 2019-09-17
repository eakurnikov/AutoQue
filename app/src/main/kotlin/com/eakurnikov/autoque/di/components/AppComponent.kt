package com.eakurnikov.autoque.di.components

import com.eakurnikov.autoque.di.modules.AppModule
import com.eakurnikov.autoque.di.modules.activity.ActivityBuilderModule
import com.eakurnikov.autoque.di.modules.autofill.AutofillFeatureApiModule
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
        AutofillFeatureApiModule::class,
        ActivityBuilderModule::class
    ]
)
interface AppComponent : AndroidInjector<AutoQueApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<AutoQueApp>()
}