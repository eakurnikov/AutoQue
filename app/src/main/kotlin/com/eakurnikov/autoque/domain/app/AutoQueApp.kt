package com.eakurnikov.autoque.domain.app

import com.eakurnikov.autoque.autofill.api.api.AutofillFeatureApi
import com.eakurnikov.autoque.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

/**
 * Created by eakurnikov on 2019-09-15
 */
class AutoQueApp : DaggerApplication() {

    @Inject
    lateinit var autofillApi: AutofillFeatureApi

    override fun applicationInjector(): AndroidInjector<out AutoQueApp> {
        return DaggerAppComponent
            .builder()
            .create(this)
            .apply { inject(this@AutoQueApp) }
    }
}