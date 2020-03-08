package com.eakurnikov.autoque.autofill.api.dependencies

import android.content.Context
import com.eakurnikov.autoque.autofill.api.dependencies.data.dao.AutofillDao
import com.eakurnikov.autoque.autofill.api.dependencies.domain.auth.AutofillAuthProvider
import com.eakurnikov.common.annotations.AppContext

/**
 * Created by eakurnikov on 2019-09-14
 */
interface AutofillDependenciesProvider {

    @AppContext
    fun provideContext(): Context

    fun provideAutofillDao(): AutofillDao

    fun provideAutofillAuthProvider(): AutofillAuthProvider<*>
}