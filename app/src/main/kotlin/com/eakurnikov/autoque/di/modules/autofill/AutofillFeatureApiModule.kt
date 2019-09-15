package com.eakurnikov.autoque.di.modules.autofill

import android.content.Context
import com.eakurnikov.autoque.autofill.api.api.AutofillFeatureApi
import com.eakurnikov.autoque.autofill.impl.di.components.AutofillFeatureComponent
import com.eakurnikov.autoque.di.components.AutofillDependenciesProviderComponent
import com.eakurnikov.common.annotations.AppContext
import com.eakurnikov.common.di.annotations.AppScope
import dagger.Module
import dagger.Provides

/**
 * Created by eakurnikov on 2019-09-15
 */
@Module
class AutofillFeatureApiModule {

    @Provides
    @AppScope
    fun provideAutofillFeatureApi(@AppContext context: Context): AutofillFeatureApi {
        return AutofillFeatureComponent.initAndGet(
            DaggerAutofillFeatureDependenciesComponent
                .builder()
                .autofillDependenciesProvider(AutofillDependenciesProviderComponent.initAndGet(context))
                .build()
        )
    }
}