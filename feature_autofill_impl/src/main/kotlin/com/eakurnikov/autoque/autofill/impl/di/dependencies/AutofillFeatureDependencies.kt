package com.eakurnikov.autoque.autofill.impl.di.dependencies

import android.content.Context
import com.eakurnikov.autoque.autofill.api.dependencies.data.dao.AutofillDao
import com.eakurnikov.autoque.autofill.api.dependencies.domain.auth.AutofillAuthProvider
import com.eakurnikov.common.annotations.AppContext

/**
 * Created by eakurnikov on 2019-09-15
 */
interface AutofillFeatureDependencies {

    @AppContext
    fun context(): Context

    fun autofillDao(): AutofillDao

    fun autofillAuthProvider(): AutofillAuthProvider<*>
}