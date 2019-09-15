package com.eakurnikov.autoque.autofill.impl.di.components

import com.eakurnikov.autoque.autofill.api.dependencies.AutofillDependenciesProvider
import com.eakurnikov.autoque.autofill.impl.di.dependencies.AutofillFeatureDependencies
import com.eakurnikov.common.di.annotations.AppScope
import dagger.Component

/**
 * Created by eakurnikov on 2019-09-15
 */
@AppScope
@Component(dependencies = [AutofillDependenciesProvider::class])
interface AutofillFeatureDependenciesComponent : AutofillFeatureDependencies